package com.example.chitchat.pojos

import java.text.SimpleDateFormat
import java.util.*


data class Mensaje(
        var message: String = "",
        var from: String = "",
        var fecha: Date = Date()

)
/*
fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}*/