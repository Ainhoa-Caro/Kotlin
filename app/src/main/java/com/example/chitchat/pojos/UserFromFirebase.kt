package com.example.chitchat.pojos

import android.media.Image
import android.net.Uri
import android.widget.EditText

data class UserFromFirebase (
        var nombreusuario: String?,
        var email: String,
        var nickglobal: String?,
        var password: String?,
        var foto: String?,
        var informacion: String?,
        var telefono: String?
        )
{
    //Contructor vacio
  constructor():this ("","","","",null,"","")

}