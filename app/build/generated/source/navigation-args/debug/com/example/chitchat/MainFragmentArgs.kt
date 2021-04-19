package com.example.chitchat

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class MainFragmentArgs(
  public val correo: String?
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("correo", this.correo)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): MainFragmentArgs {
      bundle.setClassLoader(MainFragmentArgs::class.java.classLoader)
      val __correo : String?
      if (bundle.containsKey("correo")) {
        __correo = bundle.getString("correo")
      } else {
        throw IllegalArgumentException("Required argument \"correo\" is missing and does not have an android:defaultValue")
      }
      return MainFragmentArgs(__correo)
    }
  }
}
