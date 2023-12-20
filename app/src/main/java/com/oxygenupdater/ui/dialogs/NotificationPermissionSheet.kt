package com.oxygenupdater.ui.dialogs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oxygenupdater.R
import com.oxygenupdater.internal.settings.PrefManager
import com.oxygenupdater.ui.common.CheckboxText
import com.oxygenupdater.ui.common.OutlinedIconButton
import com.oxygenupdater.ui.common.modifierMaxWidth
import com.oxygenupdater.ui.theme.PreviewThemes

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ColumnScope.NotificationPermissionSheet(hide: () -> Unit, launchPermissionRequest: () -> Unit) {
    SheetHeader(R.string.notification_permission_title)

    Text(
        text = stringResource(R.string.notification_permission_text),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .weight(1f, false)
            .verticalScroll(rememberScrollState())
    )

    var ignore by remember { mutableStateOf(false) }
    CheckboxText(
        checked = ignore, onCheckedChange = { ignore = it },
        textResId = R.string.do_not_show_again_checkbox,
        textColor = AlertDialogDefaults.textContentColor.copy(alpha = 0.87f),
        modifier = Modifier.offset(4.dp) // bring in line with Text
    )

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifierMaxWidth.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        TextButton(
            onClick = {
                PrefManager.putBoolean(PrefManager.KeyIgnoreNotificationPermissionSheet, ignore)
                hide()
            },
            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(stringResource(android.R.string.cancel))
        }

        /** Don't hide sheet here, [com.oxygenupdater.ui.main.NotificationPermission] will do it once granted */
        OutlinedIconButton(
            onClick = launchPermissionRequest,
            icon = Icons.Rounded.NotificationsNone,
            textResId = android.R.string.ok,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@PreviewThemes
@Composable
fun PreviewNotificationPermissionSheet() = PreviewModalBottomSheet {
    NotificationPermissionSheet(hide = {}, launchPermissionRequest = {})
}
