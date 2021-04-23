package com.example.chitchat.converOneToOne

import android.os.Bundle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class ChatOneToOneFragmentArgs(
  public val idChat: String?
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("idChat", this.idChat)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): ChatOneToOneFragmentArgs {
      bundle.setClassLoader(ChatOneToOneFragmentArgs::class.java.classLoader)
      val __idChat : String?
      if (bundle.containsKey("idChat")) {
        __idChat = bundle.getString("idChat")
      } else {
        throw IllegalArgumentException("Required argument \"idChat\" is missing and does not have an android:defaultValue")
      }
      return ChatOneToOneFragmentArgs(__idChat)
    }
  }
}
