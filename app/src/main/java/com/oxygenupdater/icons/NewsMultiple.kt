package com.oxygenupdater.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

val CustomIcons.NewsMultiple: ImageVector
    get() {
        if (_newsMultiple != null) return _newsMultiple!!
        _newsMultiple = materialIcon("NewsMultiple", autoMirror = true) {
            materialPath {
                moveTo(5f, 19f)
                horizontalLineToRelative(13f)
                curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
                verticalLineToRelative(0f)
                curveToRelative(0f, 0.6f, -0.4f, 1f, -1f, 1f)
                horizontalLineTo(4f)
                horizontalLineToRelative(0f)
                curveToRelative(-1.1f, 0f, -2f, -0.9f, -2f, -2f)
                verticalLineToRelative(0f)
                verticalLineTo(8f)
                curveToRelative(0f, -0.6f, 0.4f, -1f, 1f, -1f)
                horizontalLineToRelative(0f)
                curveToRelative(0.6f, 0f, 1f, 0.4f, 1f, 1f)
                verticalLineToRelative(10f)
                curveTo(4f, 18.6f, 4.4f, 19f, 5f, 19f)
                close()
                moveTo(23f, 4.5f)
                verticalLineToRelative(10.9f)
                curveToRelative(0f, 0.9f, -0.8f, 1.5f, -1.7f, 1.5f)
                horizontalLineTo(7.7f)
                curveTo(6.8f, 17f, 6f, 16.3f, 6f, 15.5f)
                verticalLineTo(4.5f)
                curveTo(6f, 3.7f, 6.8f, 3f, 7.7f, 3f)
                horizontalLineToRelative(13.6f)
                curveTo(22.2f, 3f, 23f, 3.7f, 23f, 4.5f)
                close()
                moveTo(21f, 6f)
                curveToRelative(0f, -0.6f, -0.4f, -1f, -1f, -1f)
                horizontalLineTo(9f)
                curveTo(8.4f, 5f, 8f, 5.4f, 8f, 6f)
                verticalLineToRelative(8f)
                curveToRelative(0f, 0.6f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(11f)
                curveToRelative(0.6f, 0f, 1f, -0.4f, 1f, -1f)
                verticalLineTo(6f)
                close()
                moveTo(20f, 13f)
                lineTo(20f, 13f)
                curveToRelative(0f, -0.6f, -0.4f, -1f, -1f, -1f)
                horizontalLineToRelative(-9f)
                curveToRelative(-0.6f, 0f, -1f, 0.4f, -1f, 1f)
                verticalLineToRelative(0f)
                curveToRelative(0f, 0.6f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(9f)
                curveTo(19.6f, 14f, 20f, 13.6f, 20f, 13f)
                close()
                moveTo(20f, 10f)
                lineTo(20f, 10f)
                curveToRelative(0f, -0.6f, -0.4f, -1f, -1f, -1f)
                horizontalLineToRelative(-4f)
                curveToRelative(-0.6f, 0f, -1f, 0.4f, -1f, 1f)
                verticalLineToRelative(0f)
                curveToRelative(0f, 0.6f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(4f)
                curveTo(19.6f, 11f, 20f, 10.6f, 20f, 10f)
                close()
                moveTo(20f, 7f)
                lineTo(20f, 7f)
                curveToRelative(0f, -0.6f, -0.4f, -1f, -1f, -1f)
                horizontalLineToRelative(-4f)
                curveToRelative(-0.6f, 0f, -1f, 0.4f, -1f, 1f)
                verticalLineToRelative(0f)
                curveToRelative(0f, 0.6f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(4f)
                curveTo(19.6f, 8f, 20f, 7.6f, 20f, 7f)
                close()
                moveTo(12f, 10f)
                verticalLineTo(7f)
                curveToRelative(0f, -0.6f, -0.4f, -1f, -1f, -1f)
                horizontalLineToRelative(-1f)
                curveTo(9.4f, 6f, 9f, 6.4f, 9f, 7f)
                verticalLineToRelative(3f)
                curveToRelative(0f, 0.6f, 0.4f, 1f, 1f, 1f)
                horizontalLineToRelative(1f)
                curveTo(11.6f, 11f, 12f, 10.6f, 12f, 10f)
                close()
            }
        }
        return _newsMultiple!!
    }

private var _newsMultiple: ImageVector? = null

@Preview
@Composable
private fun Preview() = PreviewIcon(CustomIcons.NewsMultiple)
