package com.example.babbelassignment.core.ext

import android.view.View

fun View.setVisibility(shouldShow: Boolean) {
    visibility = if (shouldShow) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}