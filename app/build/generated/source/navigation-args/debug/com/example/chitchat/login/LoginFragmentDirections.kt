package com.example.chitchat.login

import android.os.Bundle
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.chitchat.R
import kotlin.Int
import kotlin.String

public class LoginFragmentDirections private constructor() {
  private data class ActionLoginFragmentToMainFragment(
    public val correo: String?
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_loginFragment_to_mainFragment

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("correo", this.correo)
      return result
    }
  }

  public companion object {
    public fun actionLoginFragmentToMainFragment(correo: String?): NavDirections =
        ActionLoginFragmentToMainFragment(correo)

    public fun actionLoginFragmentToRegistroFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_loginFragment_to_registroFragment)
  }
}
