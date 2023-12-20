package com.oxygenupdater.ui.common

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.oxygenupdater.ui.theme.backgroundVariant

@OptIn(ExperimentalFoundationApi::class)
@Composable
@NonRestartableComposable
fun LazyVerticalGrid(
    columnCount: Int,
    items: Array<GridItem>,
) = LazyVerticalGrid(
    columns = GridCells.Fixed(columnCount),
    userScrollEnabled = false,
    // 32dp total vertical padding + 24dp icon
    modifier = Modifier.height((56 * (items.size / columnCount)).dp)
) {
    itemsIndexed(items) { index, it ->
        IconText(
            icon = it.icon,
            text = stringResource(it.textResId),
            style = MaterialTheme.typography.titleSmall,
            maxLines = 1, // force all grid items to be of the same 1-line height
            textModifier = Modifier.basicMarquee(),
            modifier = Modifier
                .clickable(onClick = it.onClick)
                .borderExceptTop(
                    color = MaterialTheme.colorScheme.backgroundVariant,
                    ltr = LocalLayoutDirection.current == LayoutDirection.Ltr,
                    drawEnd = (index + 1) % columnCount != 0,
                ) then modifierDefaultPadding // padding after everything else
        )
    }
}

@Immutable
class GridItem(
    val icon: ImageVector,
    @StringRes val textResId: Int,
    val onClick: () -> Unit,
)
