package com.example.chitchat.pojos

import java.util.*

data class Mensaje(
        var message: String = "",
        var from: String = "",
        var fecha: Date = Date()
)
