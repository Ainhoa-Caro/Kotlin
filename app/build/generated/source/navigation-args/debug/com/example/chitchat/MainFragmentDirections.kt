package com.example.chitchat

import android.os.Bundle
import androidx.navigation.NavDirections
import kotlin.Int
import kotlin.String

public class MainFragmentDirections private constructor() {
  private data class ActionMainFragmentToChatOneToOneFragment(
    public val idChat: String?
  ) : NavDirections {
    public override fun getActionId(): Int = R.id.action_mainFragment_to_chatOneToOneFragment

    public override fun getArguments(): Bundle {
      val result = Bundle()
      result.putString("idChat", this.idChat)
      return result
    }
  }

  public companion object {
    public fun actionMainFragmentToChatOneToOneFragment(idChat: String?): NavDirections =
        ActionMainFragmentToChatOneToOneFragment(idChat)
  }
}
