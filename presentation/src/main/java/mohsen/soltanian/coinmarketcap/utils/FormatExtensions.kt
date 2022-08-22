package mohsen.soltanian.coinmarketcap.utils

import java.text.DecimalFormat

internal val moneyFormat = DecimalFormat("#,###.0000000000")

fun Any.toMoneyFormat() = moneyFormat.format(this)