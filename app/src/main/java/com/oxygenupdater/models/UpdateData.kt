package com.oxygenupdater.models

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.work.Data
import com.oxygenupdater.OxygenUpdater
import com.oxygenupdater.internal.NotSetL
import com.oxygenupdater.workers.Md5VerificationWorker
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
@Entity(tableName = "update_data")
@JsonClass(generateAdapter = true)
data class UpdateData(
    @PrimaryKey
    val id: Long?,

    @ColumnInfo("version_number")
    @Json(name = "version_number")
    val versionNumber: String? = null,

    @ColumnInfo("ota_version_number")
    @Json(name = "ota_version_number")
    val otaVersionNumber: String? = null,

    val changelog: String? = null,
    val description: String? = null,

    @ColumnInfo("download_url")
    @Json(name = "download_url")
    val downloadUrl: String? = null,

    @ColumnInfo("download_size")
    @Json(name = "download_size")
    val downloadSize: Long = 0,

    val filename: String? = null,
    val md5sum: String? = null,
    val information: String? = null,

    @ColumnInfo("update_information_available")
    @Json(name = "update_information_available")
    val updateInformationAvailable: Boolean = false,

    @ColumnInfo("system_is_up_to_date", defaultValue = "0")
    @Json(name = "system_is_up_to_date")
    val systemIsUpToDate: Boolean = false,
) : Parcelable {

    @IgnoredOnParcel
    @Ignore
    @JvmField
    val isUpdateInformationAvailable = updateInformationAvailable || versionNumber != null

    @IgnoredOnParcel
    @Ignore
    @JvmField
    val shouldFetchMostRecent = information != null && information == OxygenUpdater.UnableToFindAMoreRecentBuild && isUpdateInformationAvailable && systemIsUpToDate

    fun toWorkData() = Data.Builder().apply {
        putLong("id", id ?: NotSetL)
        putString("versionNumber", versionNumber)
        putString("otaVersionNumber", otaVersionNumber)
        putString("description", description)
        putString("downloadUrl", downloadUrl)
        putLong("downloadSize", downloadSize)
        putString(Md5VerificationWorker.FILENAME, filename)
        putString(Md5VerificationWorker.MD5, md5sum)
        putString("information", information)
        putBoolean("updateInformationAvailable", updateInformationAvailable)
        putBoolean("systemIsUpToDate", systemIsUpToDate)
    }.build()

    companion object {
        fun getBuildDate(otaVersionNumber: String?) = otaVersionNumber?.substringAfterLast('_')?.toLongOrNull() ?: 0

        fun createFromWorkData(inputData: Data?) = if (inputData != null) UpdateData(
            id = inputData.getLong("id", NotSetL),
            versionNumber = inputData.getString("versionNumber"),
            otaVersionNumber = inputData.getString("otaVersionNumber"),
            description = inputData.getString("description"),
            downloadUrl = inputData.getString("downloadUrl"),
            downloadSize = inputData.getLong("downloadSize", NotSetL),
            filename = inputData.getString(Md5VerificationWorker.FILENAME),
            md5sum = inputData.getString(Md5VerificationWorker.MD5),
            information = inputData.getString("information"),
            updateInformationAvailable = inputData.getBoolean("updateInformationAvailable", false),
            systemIsUpToDate = inputData.getBoolean("systemIsUpToDate", false)
        ) else null
    }
}
