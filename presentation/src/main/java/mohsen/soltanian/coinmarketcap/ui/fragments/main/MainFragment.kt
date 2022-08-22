package mohsen.soltanian.coinmarketcap.ui.fragments.main

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import mohsen.soltanian.coinmarketcap.BR
import mohsen.soltanian.coinmarketcap.R
import mohsen.soltanian.coinmarketcap.base.mvi.BaseMviFragment
import mohsen.soltanian.coinmarketcap.common.SharedViewModel
import mohsen.soltanian.coinmarketcap.databinding.FragmentMainBinding
import mohsen.soltanian.coinmarketcap.enums.MenuActionType
import mohsen.soltanian.coinmarketcap.libraries.framework.core.adapter.paging.PagingLoadStateAdapter
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.annotation.FragmentAttribute
import mohsen.soltanian.coinmarketcap.ui.dialogs.filter.CryptoPercentFilterDialog
import mohsen.soltanian.coinmarketcap.ui.dialogs.filter.CryptoVolumeFilterDialog
import mohsen.soltanian.coinmarketcap.ui.dialogs.info.CryptoInfoDialog
import mohsen.soltanian.coinmarketcap.ui.fragments.main.adapter.CryptosAdapter
import mohsen.soltanian.coinmarketcap.ui.fragments.main.contract.MainContract

@FragmentAttribute(
    layoutId = R.layout.fragment_main,
    handleDoubleBackPress = true)
@AndroidEntryPoint
class MainFragment : BaseMviFragment<FragmentMainBinding, MainContract.State, MainViewModel>() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var sharedViewModel: SharedViewModel

    private val mAdapter: CryptosAdapter = CryptosAdapter().apply {
        withLoadStateHeaderAndFooter(
            header = PagingLoadStateAdapter(adapter = this),
            footer = PagingLoadStateAdapter(adapter = this)
        )
        clickListener = { data ->
            val dialog = CryptoInfoDialog.newInstance(infoModel = data)
            dialog.show(childFragmentManager, dialog.tag)
        }
    }


    override fun fragmentStart() {
        super.fragmentStart()
        me?.supportActionBar?.title = "Coin Market Cap"
    }

    override val viewModel: MainViewModel
        get() = mainViewModel

    override fun bindingVariables(): HashMap<Int, Any> {
        val hashMap: HashMap<Int, Any> = HashMap() //define empty hashmap
        hashMap[BR.viewModel] = viewModel
        hashMap[BR.adapter] = mAdapter
        hashMap[BR.rvLayoutManager] =
            LinearLayoutManager(requireActivity()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        return hashMap
    }

    override fun renderViewState(viewState: MainContract.State) {
        when (viewState) {
            is MainContract.State.CryptosData -> {
                mAdapter.submitData(lifecycle = lifecycle, pagingData = viewState.pagedData)
            }
        }
    }

    override fun onViewReady(bundle: Bundle?) {
        sharedViewModel = ViewModelProvider(owner = requireActivity())[SharedViewModel::class.java]
        sharedViewModel.sendActionType.observe(viewLifecycleOwner) { actionType ->
            when(actionType) {
                MenuActionType.FILTER_PERCENT -> {
                    showPercentFilterDialog()
                }
                MenuActionType.FILTER_VOLUME -> {
                    showVolumeFilterDialog()
                }
                else -> {
                    viewModel.actionType = actionType
                    viewModel.onTriggerEvent(MainContract.Event.fetchCryptosBySort)
                }
            }

        }
        viewModel.onTriggerEvent(MainContract.Event.fetchCryptos)
    }

    private fun showPercentFilterDialog() {
        val dialog = CryptoPercentFilterDialog(listener = object : CryptoPercentFilterDialog.OnApplyFilterClickListener{
            override fun onResult(from: String, to: String) {
                viewModel.percentMin = from
                viewModel.percentMax = to
                viewModel.onTriggerEvent(MainContract.Event.fetchCryptosByPercentFilter)
            }

            override fun onError() {
                Toast.makeText(requireActivity(),"Please fill required fields",Toast.LENGTH_SHORT).show()
            }

        })
        dialog.show(childFragmentManager, dialog.tag)
    }

    private fun showVolumeFilterDialog() {
        val dialog = CryptoVolumeFilterDialog(listener = object: CryptoVolumeFilterDialog.OnApplyFilterClickListener {
            override fun onResult(from: String, to: String) {
                viewModel.volumeMin = from
                viewModel.volumeMax = to
                viewModel.onTriggerEvent(MainContract.Event.fetchCryptosByVolumeFilter)
            }

            override fun onError() {
                Toast.makeText(requireActivity(),"Please fill required fields",Toast.LENGTH_SHORT).show()
            }

        })
        dialog.show(childFragmentManager, dialog.tag)
    }
}