package com.digvesh.core.extensions

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment

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