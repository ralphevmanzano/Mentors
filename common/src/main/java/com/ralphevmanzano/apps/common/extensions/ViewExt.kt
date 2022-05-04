package com.ralphevmanzano.apps.common.extensions

import android.view.View

fun View.show() {
    if (this.visibility != View.VISIBLE) this.visibility = View.VISIBLE
}

fun View.hide() {
    if (this.visibility != View.GONE || this.visibility != View.INVISIBLE) this.visibility =
        View.GONE
}