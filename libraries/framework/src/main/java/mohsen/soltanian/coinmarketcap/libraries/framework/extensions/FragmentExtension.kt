package mohsen.soltanian.coinmarketcap.libraries.framework.extensions

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.handleBackPress(block:() -> Unit) {
    val callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                block()
            }
        }
    activity?.onBackPressedDispatcher?.addCallback(this, callback)
}

@Suppress("unused")
inline fun <reified T : Any> Fragment.argument(key: String, default: T? = null): Lazy<T> {
    return lazy {
        val value = arguments?.get(key)
        requireNotNull(if (value is T) value else default) { key }
    }
}