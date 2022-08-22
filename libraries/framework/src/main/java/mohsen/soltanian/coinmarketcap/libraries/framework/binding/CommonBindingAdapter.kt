package mohsen.soltanian.coinmarketcap.libraries.framework.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mohsen.soltanian.coinmarketcap.libraries.framework.R
import mohsen.soltanian.coinmarketcap.libraries.framework.core.adapter.BasicRecyclerAdapter
import mohsen.soltanian.coinmarketcap.libraries.framework.extensions.setSafeOnClickListener

object CommonBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
    fun loadUrl(view: ImageView, url: String?, placeHolder: Drawable?) {
        Glide.with(view.context).load(url).placeholder(placeHolder).into(view)
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun booleanToVisibility(view: View,value: Boolean?) =
        if (value == true) view.visibility = View.VISIBLE else view.visibility = View.GONE

    @JvmStatic
    @BindingAdapter("safeOnClick")
    fun safeOnClick(view: View,callback: () -> Unit) {
        view.setSafeOnClickListener(debounceTime = 1200) {
            callback()
        }
    }


}