package com.example.chitchat.pojos

import android.media.Image
import android.net.Uri
import android.widget.EditText

data class User (
        var nombreusuario: String?,
        var email: String,
        var nickglobal: String?,
        var password: String?,
        var foto: Uri?,
        var informacion: String?,
        var telefono: Int?)
{
    //Contructor vacio
  constructor():this ("","","","",null,"",0)

}