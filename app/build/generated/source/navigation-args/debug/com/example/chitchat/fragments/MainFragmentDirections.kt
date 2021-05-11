package com.example.chitchat.fragments

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.chitchat.R
import kotlin.Int
import kotlin.String

public class MainFragmentDirections private constructor() {
  private data class ActionMainFragmentToConversacionOneToOne(
    public val idConversacion: String
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_mainFragment_to_conversacionOneToOne

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("idConversacion", this.idConversacion)
      return result
    }
  }

  public companion object {
    public fun actionMainFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_loginFragment)

    public fun actionMainFragmentToSeleccionContactoFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_seleccionContactoFragment)

    public fun actionMainFragmentToConversacionOneToOne(idConversacion: String): NavDirections =
        ActionMainFragmentToConversacionOneToOne(idConversacion)
  }
}
