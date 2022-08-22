package mohsen.soltanian.coinmarketcap.enums

enum class MenuActionType(val type: String) {
    NONE(""),
    SORT_BY_NAME("name"),
    SORT_BY_PRICE("price"),
    SORT_BY_MARKET_CAP("market_cap"),
    SORT_BY_CIRCULATING_SUPPLY("circulating_supply"),
    FILTER_PERCENT(""),
    FILTER_VOLUME("")
}