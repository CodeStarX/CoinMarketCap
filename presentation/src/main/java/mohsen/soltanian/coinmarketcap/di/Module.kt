package mohsen.soltanian.coinmarketcap.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mohsen.soltanian.coinmarketcap.app.Application
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.application.AppInitializer
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.application.AppInitializerImpl
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.application.CoreConfig
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.application.TimberInitializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideApplication(): Application = Application()


    @Provides
    @Singleton
    fun provideAppConfig(app: Application): CoreConfig = app.appConfig()


    @Provides
    @Singleton
    fun provideTimberInitializer() = TimberInitializer()

    @Provides
    @Singleton
    fun provideAppInitializer(timberManager: TimberInitializer): AppInitializer {
        return AppInitializerImpl(timberManager)
    }
}