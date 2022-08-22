package mohsen.soltanian.coinmarketcap.ui.dialogs.filter

import android.os.Bundle
import mohsen.soltanian.coinmarketcap.base.sheetDialog.BaseSheetDialog
import mohsen.soltanian.coinmarketcap.databinding.BottomSheetVolumeFilterCurrencyBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.setSafeOnClickListener
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.textChangeListener

class CryptoVolumeFilterDialog(private val listener: OnApplyFilterClickListener) :
    BaseSheetDialog<BottomSheetVolumeFilterCurrencyBinding>() {

    private var from: String = ""
    private var to: String = ""

    interface OnApplyFilterClickListener {
        fun onResult(from: String, to: String)
        fun onError()
    }

    override fun onViewReady(bundle: Bundle?) {
        binding.edtFrom.requestFocus()
        binding.edtFrom.textChangeListener { value -> from = value }
        binding.edtTo.textChangeListener { value -> to = value }
        binding.btn.setSafeOnClickListener {
            if (from.isNotEmpty() && to.isNotEmpty()) {
                dismiss()
                listener.onResult(from = from, to = to)
            } else {
                listener.onError()
            }
        }
    }
}