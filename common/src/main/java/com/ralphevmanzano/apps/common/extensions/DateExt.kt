package com.ralphevmanzano.apps.common.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(): String {
    val formatted = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return formatted.format(time)
}

fun Date.formatDateTime(): String {
    val formatted = SimpleDateFormat("MMM dd, yyyy hh:mm aa", Locale.getDefault())
    return formatted.format(time)
}