package mohsen.soltanian.coinmarketcap.libraries.framework.core.base.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import mohsen.soltanian.coinmarketcap.libraries.framework.core.adapter.BasicPagingRecyclerAdapter
import mohsen.soltanian.coinmarketcap.libraries.framework.core.adapter.BasicRecyclerAdapter
import mohsen.soltanian.coinmarketcap.libraries.framework.core.base.annotation.Layout
import java.lang.reflect.ParameterizedType

inline fun <reified V : ViewDataBinding> BasicRecyclerAdapter<*, *>.getDataBinding(
    layoutInflater: LayoutInflater,container: ViewGroup): V? {
    return if(this.javaClass.getAnnotation(Layout::class.java) == null) {
        throw Exception("layout id is Null")
        null
    }else {
        this.javaClass.getAnnotation(Layout::class.java)
            ?.let { layoutRes -> DataBindingUtil.inflate(layoutInflater, layoutRes.value,container,false) }

    }
}

inline fun <reified V : ViewDataBinding> BasicPagingRecyclerAdapter<*, *>.getDataBinding(
    layoutInflater: LayoutInflater,container: ViewGroup): V? {
    return if(this.javaClass.getAnnotation(Layout::class.java) == null) {
        throw Exception("layout id is Null")
        null
    }else {
        this.javaClass.getAnnotation(Layout::class.java)
            ?.let { layoutRes -> DataBindingUtil.inflate(layoutInflater, layoutRes.value,container,false) }

    }
}


internal fun <V : ViewBinding> BindingSheetDialog<V>.getBinding(
    inflater: LayoutInflater,
    container: ViewGroup?
): V {
    return findClass().getBinding(inflater, container)
}

internal fun Any.findClass(): Class<*> {
    var javaClass: Class<*> = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkMethod()) {
        result = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.firstOrNull {
                if (it is Class<*>) {
                    it.checkMethod()
                } else {
                    false
                }
            } as? Class<*>
        javaClass = javaClass.superclass
    }
    return result
}

internal fun <V : ViewBinding> Class<*>.getBinding(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        ).invoke(null, layoutInflater, container, false) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}

internal fun Class<*>.checkMethod(): Boolean {
    return try {
        getMethod(
            "inflate",
            LayoutInflater::class.java
        )
        true
    } catch (ex: Exception) {
        false
    }
}
