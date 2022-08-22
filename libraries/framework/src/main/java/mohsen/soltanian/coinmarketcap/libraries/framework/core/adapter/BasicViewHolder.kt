package mohsen.soltanian.coinmarketcap.libraries.framework.core.adapter

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.binding.BindingViewHolder

/**
 * A Simple [BasicViewHolder] providing easier support for ViewBinding
 */
class BasicViewHolder<VB : ViewDataBinding?>(binding: VB) : BindingViewHolder<VB>(binding)
