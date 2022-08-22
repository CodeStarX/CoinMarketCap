package mohsen.soltanian.coinmarketcap.libraries.framework.core.base.mvi

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.mvvm.MvvmFragment
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.observeFlowStart

abstract class MviFragment<VB : ViewDataBinding, STATE, VM : MviViewModel<STATE, *>> :
    MvvmFragment<VB, VM>() {

    open fun renderViewState(viewState: STATE){}

    override fun observeUi() {
        super.observeUi()
        observeFlowStart(viewModel.stateFlow, ::renderViewState)
    }
}
