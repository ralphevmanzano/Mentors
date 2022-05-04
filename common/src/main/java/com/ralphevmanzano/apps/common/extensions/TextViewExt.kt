package com.ralphevmanzano.apps.common.extensions

import android.widget.TextView

fun TextView.showOrHide(text: String?) {
    if (text.isNullOrEmpty()) hide()
    else this.text = text
}