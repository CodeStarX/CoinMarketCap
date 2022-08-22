package mohsen.soltanian.coinmarketcap.ui.activities.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import mohsen.soltanian.coinmarketcap.R
import mohsen.soltanian.coinmarketcap.common.SharedViewModel
import mohsen.soltanian.coinmarketcap.enums.MenuActionType

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = ""
        sharedViewModel = ViewModelProvider(this@MainActivity)[SharedViewModel::class.java]
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemSortByName -> {
                sharedViewModel.sendActionType.value = MenuActionType.SORT_BY_NAME
            }
            R.id.itemSortByPrice -> {
                sharedViewModel.sendActionType.value = MenuActionType.SORT_BY_PRICE
            }
            R.id.itemSortByMarketCap -> {
                sharedViewModel.sendActionType.value = MenuActionType.SORT_BY_MARKET_CAP
            }
            R.id.itemSortByCirculatingSupply -> {
                sharedViewModel.sendActionType.value = MenuActionType.SORT_BY_CIRCULATING_SUPPLY
            }
            R.id.itemFilterPercent -> {
                sharedViewModel.sendActionType.value = MenuActionType.FILTER_PERCENT
            }
            R.id.itemFilterVolume -> {
                sharedViewModel.sendActionType.value = MenuActionType.FILTER_VOLUME
            }
        }
        return super.onOptionsItemSelected(item)
    }
}