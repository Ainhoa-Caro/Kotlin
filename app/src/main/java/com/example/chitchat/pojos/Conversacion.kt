package com.example.chitchat.pojos

import java.util.*

data class Conversacion(
        var id: String = "",
        var name: String = "",
        var userLoginId: String = "",
        var otherUserId: String = "",
        var mensajesList: MutableList<Mensaje>
){
    constructor() : this("","", "", "", arrayListOf<Mensaje>())
}

