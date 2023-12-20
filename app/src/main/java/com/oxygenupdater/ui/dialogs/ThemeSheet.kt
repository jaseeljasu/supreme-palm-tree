package com.oxygenupdater.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oxygenupdater.R
import com.oxygenupdater.internal.settings.PrefManager
import com.oxygenupdater.ui.Theme
import com.oxygenupdater.ui.common.animatedClickable
import com.oxygenupdater.ui.common.modifierDefaultPadding
import com.oxygenupdater.ui.theme.PreviewThemes

private val list = arrayOf(
    Theme.Light,
    Theme.Dark,
    Theme.System,
    Theme.Auto,
)

@Composable
fun ColumnScope.ThemeSheet(
    hide: () -> Unit,
    onClick: (Theme) -> Unit,
) {
    SheetHeader(R.string.label_theme)

    val selectedTheme = remember { PrefManager.theme }

    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    // Perf: re-use common modifiers to avoid recreating the same object repeatedly
    val iconSpacerSizeModifier = Modifier.size(40.dp) // 24 + 16
    LazyColumn(Modifier.weight(1f, false)) {
        items(items = list, key = { it.value }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .animatedClickable {
                        PrefManager.putInt(PrefManager.ThemeId, it.value)
                        onClick(it)
                        hide()
                    } then modifierDefaultPadding // must be after `clickable`
            ) {
                val primary = colorScheme.primary
                val selected = selectedTheme == it
                if (selected) Icon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = stringResource(R.string.summary_on),
                    tint = primary,
                    modifier = Modifier.padding(end = 16.dp)
                ) else Spacer(iconSpacerSizeModifier)

                Column {
                    Text(
                        text = stringResource(it.titleResId),
                        color = if (selected) primary else Color.Unspecified,
                        style = typography.titleSmall,
                    )
                    Text(
                        text = stringResource(it.subtitleResId),
                        color = if (selected) primary else colorScheme.onSurfaceVariant,
                        style = typography.bodySmall,
                    )
                }
            }
        }
    }

    SheetCaption(R.string.theme_additional_note)
}

@PreviewThemes
@Composable
fun PreviewThemeSheet() = PreviewModalBottomSheet {
    ThemeSheet(hide = {}) {}
}
