package com.example.chitchat.pojos

import java.util.*

data class Mensaje(
        var contenidoMensaje: String = "",
        var emisorId: String = "",
        var fecha: Date = Date()

)
