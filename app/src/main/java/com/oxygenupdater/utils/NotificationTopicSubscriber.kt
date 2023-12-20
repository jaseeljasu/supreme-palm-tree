package com.oxygenupdater.utils

import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.oxygenupdater.BuildConfig.NOTIFICATIONS_PREFIX
import com.oxygenupdater.internal.NotSetL
import com.oxygenupdater.internal.settings.PrefManager
import com.oxygenupdater.internal.settings.PrefManager.KeyDeviceId
import com.oxygenupdater.internal.settings.PrefManager.KeyNotificationTopic
import com.oxygenupdater.internal.settings.PrefManager.KeyUpdateMethodId
import com.oxygenupdater.models.Device
import com.oxygenupdater.models.UpdateMethod
import com.oxygenupdater.utils.Logger.logDebug
import com.oxygenupdater.utils.Logger.logWarning

object NotificationTopicSubscriber {

    private const val TAG = "NotificationTopicSubscriber"
    private const val PREFIX = "${NOTIFICATIONS_PREFIX}device_"
    private const val UpdateMethodTopicPrefix = "_update-method_"

    private val topic: String
        get() {
            val deviceId = PrefManager.getLong(KeyDeviceId, NotSetL)
            val updateMethodId = PrefManager.getLong(KeyUpdateMethodId, NotSetL)

            return PREFIX + deviceId + UpdateMethodTopicPrefix + updateMethodId
        }

    private val messaging by lazy(LazyThreadSafetyMode.NONE) {
        Firebase.messaging
    }

    private fun unsubscribeFromTopic(topic: String) {
        messaging.unsubscribeFromTopic(topic)
        logDebug(TAG, "Unsubscribed from topic: $topic")
    }

    private fun unsubscribeFromOldTopics(
        deviceList: List<Device>,
        updateMethodList: List<UpdateMethod>,
        oldTopic: String?,
    ) = if (oldTopic.isNullOrEmpty()) updateMethodList.forEach { method ->
        // If the topic is not saved (App Version 1.0.0 did not do this),
        // unsubscribe from all possible topics first to prevent duplicate/wrong notifications.

        // Allocate here to avoid repeated string concatenation
        val methodTopic = UpdateMethodTopicPrefix + method.id

        deviceList.forEach { device ->
            unsubscribeFromTopic(PREFIX + device.id + methodTopic)
        }
    } else unsubscribeFromTopic(oldTopic)

    /**
     * Subscribes to the new topic to start receiving notifications
     */
    private fun subscribeToNewTopic(newTopic: String) {
        messaging.subscribeToTopic(newTopic)
        PrefManager.putString(KeyNotificationTopic, newTopic)
        logDebug(TAG, "Subscribed to topic: $newTopic")
    }

    /**
     * Unsubscribes from old topics to avoid duplicate/incorrect notifications,
     * after which it subscribes to the new topic (based on the currently selected
     * device & update method)
     */
    fun resubscribe(
        deviceList: List<Device>,
        updateMethodList: List<UpdateMethod>,
        oldTopic: String? = PrefManager.getString(KeyNotificationTopic, null),
        newTopic: String = this.topic,
    ) {
        unsubscribeFromOldTopics(deviceList, updateMethodList, oldTopic)
        subscribeToNewTopic(newTopic)
    }

    /**
     * Resubscribes only if the saved topic is `null` (v1.0.0 didn't save it),
     * or if it isn't what it's supposed to be: either due to an incorrect format,
     * device ID, or update method ID.
     *
     * This probably happens if the user changes device/update method so quick
     * that the app simply can't resubscribe on time (network issues).
     * Or, if the user has manually edited shared preferences to change things.
     */
    fun resubscribeIfNeeded(
        deviceList: List<Device>,
        updateMethodList: List<UpdateMethod>,
        newTopic: String = this.topic,
    ) {
        val oldTopic = PrefManager.getString(KeyNotificationTopic, null)
        if (oldTopic.isNullOrEmpty() || oldTopic != newTopic) {
            logWarning(TAG, "Resubscribing: saved topic is incorrect ($oldTopic vs $newTopic)")
            resubscribe(deviceList, updateMethodList, oldTopic, newTopic)
        }
    }
}
