package com.example.gonetexam.common

import java.text.SimpleDateFormat
import java.util.*


fun String.formatServerDateTo(format: String): String {
        val currentDate: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(this)
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return if (currentDate != null) sdf.format(currentDate) else ""
}
