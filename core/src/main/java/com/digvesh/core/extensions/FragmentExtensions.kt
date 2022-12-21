package com.digvesh.core.extensions

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

fun Fragment.showToast(message: String) {
    Toast.makeText(
        requireContext(),
        message,
        Toast.LENGTH_SHORT
    ).show()
}

fun Fragment.putBundleInt(key: String?, value: Int) {
    val bundle = arguments ?: Bundle()
    bundle.putInt(key, value)
    arguments = bundle
}

fun loadImage(context: Context?, url: String?, view: ImageView) {
    context?.let {
        Glide.with(it).load(url).into(view)
    }
}