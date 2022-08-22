package mohsen.soltanian.coinmarketcap.libraries.framework.core.base.application

interface CoreConfigProvider<T : CoreConfig> {
    fun appConfig(): T
}
