package com.oxygenupdater.models

import androidx.compose.runtime.Immutable

/**
 * Used primarily in [com.oxygenupdater.utils.Utils.checkDeviceOsSpec]
 *
 * @author [Adhiraj Singh Chauhan](https://github.com/adhirajsinghchauhan)
 */
@Immutable
@JvmInline
value class DeviceOsSpec(val value: Int) {

    override fun toString() = "DeviceOsSpec." + when (this) {
        SupportedOxygenOs -> "SupportedOxygenOs"
        CarrierExclusiveOxygenOs -> "CarrierExclusiveOxygenOs"
        UnsupportedOxygenOs -> "UnsupportedOxygenOs"
        UnsupportedOs -> "UnsupportedOs"
        else -> "Invalid"
    }

    companion object {
        /** For devices that are supported by the app */
        val SupportedOxygenOs = DeviceOsSpec(0)

        /**
         * For carrier-exclusive devices like OnePlus 7T Pro 5G McLaren (T-Mobile).
         * These devices aren't supported by the app either, due to the "Local Upgrade" option being missing.
         */
        val CarrierExclusiveOxygenOs = DeviceOsSpec(1)

        /** For new devices that haven't been added to the app yet */
        val UnsupportedOxygenOs = DeviceOsSpec(2)

        /** For devices that aren't running OxygenOS. This may include OnePlus devices with a custom ROM installed. */
        val UnsupportedOs = DeviceOsSpec(3)
    }
}
