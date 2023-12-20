package com.oxygenupdater.utils

import android.os.Build
import com.oxygenupdater.models.UpdateData
import com.oxygenupdater.utils.UpdateDataVersionFormatter.getFormattedOxygenOsVersion
import com.oxygenupdater.utils.UpdateDataVersionFormatter.getFormattedVersionNumber
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateDataVersionFormatterTest {

    @Test
    fun `check if version number is formatted correctly`() {
        // Default
        assertEquals("", getFormattedVersionNumber(UpdateData(id = 1), ""))
        assertEquals("default", getFormattedVersionNumber(UpdateData(id = 1), "default"))

        // Can be extracted from `description`'s first line
        assertEquals("OxygenOS Closed Beta 1", getFormattedVersionNumber(UpdateData(id = 1, description = "#Alpha_1")))
        assertEquals("OxygenOS Open Beta 1", getFormattedVersionNumber(UpdateData(id = 1, description = "#Open_1")))
        assertEquals("OxygenOS Open Beta 1", getFormattedVersionNumber(UpdateData(id = 1, description = "#Beta_1")))
        assertEquals("Android ${Build.VERSION.RELEASE} DP 1", getFormattedVersionNumber(UpdateData(id = 1, description = "#DP_1")))
        assertEquals("OxygenOS 1.2.3", getFormattedVersionNumber(UpdateData(id = 1, description = "#1.2.3")))
        assertEquals("OxygenOS 1.2.3.4", getFormattedVersionNumber(UpdateData(id = 1, description = "#1.2.3.4")))
        assertEquals("OxygenOS 1.2.3.4.AB01CD", getFormattedVersionNumber(UpdateData(id = 1, description = "#1.2.3.4.AB01CD")))
        assertEquals("Custom version", getFormattedVersionNumber(UpdateData(id = 1, description = "#Custom version")))

        // Fallback to `versionNumber`
        assertEquals("XY1234_11_A.01", getFormattedVersionNumber(UpdateData(id = 1, versionNumber = "XY1234_11_A.01")))
    }

    @Test
    fun `check if OS version is formatted correctly`() {
        // Invalid
        assertEquals(Build.UNKNOWN, getFormattedOxygenOsVersion(""))
        assertEquals(Build.UNKNOWN, getFormattedOxygenOsVersion(" "))

        // Alpha, beta, or developer preview
        assertEquals("Closed Beta 1", getFormattedOxygenOsVersion("Alpha_1"))
        assertEquals("Open Beta 1", getFormattedOxygenOsVersion("Open_1"))
        assertEquals("Open Beta 1", getFormattedOxygenOsVersion("Beta_1"))
        assertEquals("Android ${Build.VERSION.RELEASE} DP 1", getFormattedOxygenOsVersion("DP_1"))

        // Stable
        assertEquals("1.2.3", getFormattedOxygenOsVersion("1.2.3"))
        assertEquals("1.2.3.4", getFormattedOxygenOsVersion("1.2.3.4"))
        assertEquals("1.2.3.4.AB01CD", getFormattedOxygenOsVersion("1.2.3.4.AB01CD"))

        // Anything else
        assertEquals("Custom version", getFormattedOxygenOsVersion("Custom version"))
    }
}
