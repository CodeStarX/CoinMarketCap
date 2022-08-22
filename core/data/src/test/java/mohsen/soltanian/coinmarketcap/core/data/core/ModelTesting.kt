package mohsen.soltanian.coinmarketcap.core.data.core

import kotlin.reflect.KClass

@kotlin.annotation.Target(AnnotationTarget.CLASS)
annotation class ModelTesting(val clazz: KClass<*>, val modelFields: Array<String> = [])
