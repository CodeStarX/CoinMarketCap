package mohsen.soltanian.coinmarketcap.libraries.framework.core.base.mvi

import androidx.databinding.ViewDataBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.mvvm.MvvmActivity
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.observeFlowStart

abstract class MviActivity<VB : ViewDataBinding, STATE, VM : MviViewModel<STATE, *>> :
    MvvmActivity<VB, VM>() {

    abstract fun renderViewState(viewState: STATE)

    override fun observeUi() {
        super.observeUi()
        observeFlowStart(viewModel.stateFlow, ::renderViewState)
    }
}
