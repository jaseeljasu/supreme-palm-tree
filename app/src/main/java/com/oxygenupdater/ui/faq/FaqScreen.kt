package com.oxygenupdater.ui.faq

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandLess
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.AdView
import com.oxygenupdater.BuildConfig
import com.oxygenupdater.models.InAppFaq
import com.oxygenupdater.ui.RefreshAwareState
import com.oxygenupdater.ui.common.BannerAd
import com.oxygenupdater.ui.common.ExpandCollapse
import com.oxygenupdater.ui.common.IconText
import com.oxygenupdater.ui.common.ItemDivider
import com.oxygenupdater.ui.common.RichText
import com.oxygenupdater.ui.common.adLoadListener
import com.oxygenupdater.ui.common.animatedClickable
import com.oxygenupdater.ui.common.modifierDefaultPadding
import com.oxygenupdater.ui.common.modifierDefaultPaddingStartTopEnd
import com.oxygenupdater.ui.common.modifierMaxWidth
import com.oxygenupdater.ui.common.rememberSaveableState
import com.oxygenupdater.ui.common.withPlaceholder
import com.oxygenupdater.ui.theme.PreviewAppTheme
import com.oxygenupdater.ui.theme.PreviewThemes

@Composable
fun FaqScreen(
    modifier: Modifier,
    state: RefreshAwareState<List<InAppFaq>>,
    showAds: Boolean,
    bannerAdInit: (AdView) -> Unit,
) = Column(modifier) {
    val (refreshing, data) = state
    val list = if (!refreshing) rememberSaveable(data) { data } else data
    val lastIndex = list.lastIndex
    var adLoaded by rememberSaveableState("adLoaded", false)

    // Perf: re-use common modifiers to avoid recreating the same object repeatedly
    val typography = MaterialTheme.typography
    val titleMedium = typography.titleMedium
    val categoryModifier = modifierDefaultPaddingStartTopEnd.withPlaceholder(refreshing, titleMedium)

    val itemPlaceholderModifier = Modifier.withPlaceholder(refreshing, typography.bodyMedium)
    val itemTextModifier = Modifier.padding(start = 56.dp, end = 16.dp, bottom = 16.dp)

    LazyColumn(Modifier.weight(1f)) {
        itemsIndexed(
            items = list,
            // Since the server flattens categories and items into a single JSON
            // array, we need to avoid `id` collisions. 10000 should be enough.
            key = { _, it ->
                it.id + if (it.type == TypeCategory) 10000 else 0
            },
            contentType = { _, it -> it.type },
        ) { index, it ->
            if (it.type == TypeCategory) {
                Text(
                    text = it.title ?: "",
                    style = titleMedium,
                    modifier = categoryModifier
                )
            } else FaqItem(
                item = it,
                last = index == lastIndex,
                adLoaded = adLoaded,
                textModifier = itemTextModifier,
                modifier = itemPlaceholderModifier
            )
        }
    }

    if (showAds) BannerAd(
        adUnitId = BuildConfig.AD_BANNER_FAQ_ID,
        // We draw the activity edge-to-edge, so nav bar padding should be applied only if ad loaded
        adListener = adLoadListener { adLoaded = it },
        viewUpdated = bannerAdInit,
        modifier = if (adLoaded) Modifier.navigationBarsPadding() else Modifier
    )
}

@Composable
private fun FaqItem(
    item: InAppFaq,
    last: Boolean,
    adLoaded: Boolean,
    modifier: Modifier,
    textModifier: Modifier,
) {
    var expanded by remember { item.expanded }
    IconText(
        icon = if (expanded) Icons.Rounded.ExpandLess else Icons.Rounded.ExpandMore,
        text = item.title ?: "",
        textModifier = modifier,
        modifier = modifierMaxWidth
            .animatedClickable { expanded = !expanded }
            .then(modifierDefaultPadding) // must be after `clickable`
    )

    ExpandCollapse(
        visible = expanded,
        // Don't re-consume navigation bar insets
        modifier = if (last && !adLoaded) Modifier.navigationBarsPadding() else Modifier
    ) {
        RichText(
            text = item.body,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = textModifier then modifier
        )
    }

    if (!last) ItemDivider()
}

@PreviewThemes
@Composable
fun PreviewFaqScreen() = PreviewAppTheme {
    FaqScreen(
        state = RefreshAwareState(
            false, listOf(
                InAppFaq(
                    id = 1,
                    title = PreviewTitle,
                    body = null,
                    type = TypeCategory,
                ),
                InAppFaq(
                    id = 1,
                    title = PreviewTitle,
                    body = PreviewBodyHtml,
                    type = TypeItem,
                ),
            )
        ),
        showAds = true,
        bannerAdInit = {},
        modifier = Modifier
    )
}

private const val TypeCategory = "category"
private const val TypeItem = "item"

private const val PreviewTitle = "An unnecessarily long FAQ entry, to get an accurate understanding of how long text is rendered"
private const val PreviewBodyHtml = """HTML markup, should be correctly styled:
<span style="background:red">backg</span><span style="background-color:red">round</span>&nbsp;<span style="color:red">foreground</span>
<small>small</small>&nbsp;<big>big</big>
<s>strikethrough</s>
<strong>bo</strong><b>ld</b>&nbsp;<em>ita</em><i>lic</i>&nbsp;<strong><em>bold&amp;</em></strong><em><strong>italic</strong></em>
<sub>sub</sub><sup>super</sup>script
<u>underline</u>
<a href="https://oxygenupdater.com/">link</a>
<script>script tag should render as plain text</script>"""
