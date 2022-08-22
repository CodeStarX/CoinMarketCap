package mohsen.soltanian.coinmarketcap.ui.fragments.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import mohsen.soltanian.coinmarketcap.BR
import mohsen.soltanian.coinmarketcap.R
import mohsen.soltanian.coinmarketcap.core.domain.entities.CryptoCurrency
import mohsen.soltanian.coinmarketcap.databinding.RowCryptoBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.core.adapter.BasicPagingRecyclerAdapter
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.annotation.Layout
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.binding.getDataBinding
import mohsen.soltanian.coinmarketcap.utils.toMoneyFormat

@SuppressLint("NonConstantResourceId")
@Layout(value = R.layout.row_crypto)
class CryptosAdapter :
    BasicPagingRecyclerAdapter<CryptoCurrency, RowCryptoBinding?>(diffCallback = Comparator) {

    companion object Comparator : DiffUtil.ItemCallback<CryptoCurrency>() {
        override fun areItemsTheSame(
            oldItem: CryptoCurrency,
            newItem: CryptoCurrency
        ) =
            oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: CryptoCurrency,
            newItem: CryptoCurrency
        ) =
            oldItem == newItem
    }

    internal var clickListener: (CryptoCurrency) -> Unit = { _ -> }

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RowCryptoBinding? {
        return getDataBinding(layoutInflater = inflater, container = parent)
    }

    override fun bindView(
        ctx: Context,
        binding: RowCryptoBinding?,
        position: Int,
        item: CryptoCurrency
    ) {

        binding?.setVariable(BR.model, item)
        binding?.setVariable(BR.click, ClickProxy())
        item.price?.let { price ->
            binding?.currencyPrice?.text =
                "$${String.format(ctx.getString(R.string.currency_list_price), price.toMoneyFormat())}"

        } ?: ctx.getString(R.string.currency_list_price_unknown)

        item.volume_change_24h?.let { volumeChange ->
            if (volumeChange <= 0) {
                binding?.currencyPriceAtLastHour?.setTextColor(
                    ContextCompat.getColor(
                        ctx,
                        R.color.colorCurrencyPriceDown
                    )
                )
            } else {
                binding?.currencyPriceAtLastHour?.setTextColor(
                    ContextCompat.getColor(
                        ctx,
                        R.color.colorCurrencyPriceUp
                    )
                )
            }
        } ?: ctx.getString(R.string.currency_list_price_unknown)
    }

    inner class ClickProxy {
        fun itemSelection(data: CryptoCurrency) {
            clickListener(data)
        }
    }

}
