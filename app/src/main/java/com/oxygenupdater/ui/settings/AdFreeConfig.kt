package com.oxygenupdater.ui.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import com.oxygenupdater.R
import com.oxygenupdater.enums.PurchaseType
import com.oxygenupdater.repositories.BillingRepository.SkuState
import com.oxygenupdater.utils.Logger.logBillingError

@Composable
inline fun adFreeConfig(
    state: SkuState?,
    crossinline makePurchase: @DisallowComposableCalls (PurchaseType) -> Unit,
    crossinline markPending: @DisallowComposableCalls () -> Unit,
) = remember(state) {
    when (state) {
        SkuState.Unknown -> {
            logBillingError("AdFreeConfig", "SKU '${PurchaseType.AD_FREE.sku}' is not available")
            Triple(false, R.string.settings_buy_button_not_possible, null)
        }

        SkuState.NotPurchased -> Triple(true, R.string.settings_buy_button_buy) { makePurchase(PurchaseType.AD_FREE) }
        SkuState.PurchaseInitiated -> Triple(false, R.string.summary_please_wait, null)

        SkuState.Pending -> {
            markPending()
            Triple(false, R.string.processing, null)
        }

        SkuState.PurchasedAndAcknowledged -> Triple(false, R.string.settings_buy_button_bought, null)

        // PURCHASED => already bought, but not yet acknowledged by the app.
        // This should never happen, as it's already handled within BillingDataSource.
        null, SkuState.Purchased -> null
        else -> null
    }
}
