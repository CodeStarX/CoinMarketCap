package mohsen.soltanian.coinmarketcap.ui.dialogs.info

import android.os.Bundle
import androidx.core.os.bundleOf
import mohsen.soltanian.coinmarketcap.R
import mohsen.soltanian.coinmarketcap.base.sheetDialog.BaseSheetDialog
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import mohsen.soltanian.coinmarketcap.databinding.BottomSheetCurrencyBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.argument
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.loadFromUrl
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.setSafeOnClickListener
import mohsen.soltanian.coinmarketcap.utils.toMoneyFormat

class CryptoInfoDialog : BaseSheetDialog<BottomSheetCurrencyBinding>() {

    companion object {
        private const val ARG_MODEL = "arg_info_model"

        @JvmStatic
        fun newInstance(infoModel: CryptoCurrency) = CryptoInfoDialog().apply {
            arguments = bundleOf(ARG_MODEL to infoModel)
        }
    }

    private val infoModel: CryptoCurrency? by argument(ARG_MODEL)

    override fun onViewReady(bundle: Bundle?) {
        infoModel?.let { model ->
            binding.currencyImage.loadFromUrl(model.imageUrl.toString())
            binding.currencyName.text = model.name
            binding.currencySymbol.text = model.symbol
            binding.currencySlug.text = model.slug
            binding.currencyPrice.text = model.price.let {price ->
                "$${String.format(getString(R.string.currency_list_price), price?.toMoneyFormat())}"
            } ?: getString(R.string.currency_list_price_unknown)
        }

        binding.btnOK.setSafeOnClickListener { dismiss() }
    }
}