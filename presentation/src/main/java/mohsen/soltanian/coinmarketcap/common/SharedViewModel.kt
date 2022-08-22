package mohsen.soltanian.coinmarketcap.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mohsen.soltanian.coinmarketcap.enums.MenuActionType

class SharedViewModel: ViewModel() {
   val sendActionType: MutableLiveData<MenuActionType> = MutableLiveData()
}