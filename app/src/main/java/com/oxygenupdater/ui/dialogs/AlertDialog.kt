package com.oxygenupdater.ui.dialogs

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.oxygenupdater.R
import com.oxygenupdater.ui.common.OutlinedIconButton

@Composable
@NonRestartableComposable
fun AlertDialog(
    action: (result: Boolean) -> Unit,
    @StringRes titleResId: Int,
    text: String,
    confirmIconAndResId: Pair<ImageVector, Int>? = null,
    content: @Composable (ColumnScope.() -> Unit)? = null,
) = AlertDialog(
    onDismissRequest = { action(false) },
    confirmButton = confirm@{
        val (icon, resId) = confirmIconAndResId ?: return@confirm
        OutlinedIconButton(
            onClick = { action(true) },
            icon = icon,
            textResId = resId,
        )
    },
    dismissButton = {
        TextButton(
            onClick = { action(false) },
            colors = textButtonColors(contentColor = MaterialTheme.colorScheme.error),
        ) {
            Text(stringResource(R.string.download_error_close))
        }
    },
    title = { Text(stringResource(titleResId)) },
    text = {
        if (content != null) Column {
            Text(text)
            content()
        } else Text(text)
    },
    properties = NonCancellableDialog,
)
