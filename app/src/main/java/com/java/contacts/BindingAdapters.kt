package com.java.contacts

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:isVisible")
fun isVisible(view: View, value: Boolean) {

    view.visibility = if (value)
        View.VISIBLE
    else
        View.GONE

}