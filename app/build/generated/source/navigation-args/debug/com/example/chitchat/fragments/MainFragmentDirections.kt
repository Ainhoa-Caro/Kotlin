package com.example.chitchat.fragments

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.chitchat.R

public class MainFragmentDirections private constructor() {
  public companion object {
    public fun actionMainFragmentToLoginFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_loginFragment)

    public fun actionMainFragmentToSeleccionContactoFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_seleccionContactoFragment)

    public fun actionMainFragmentToConversacionOneToOne(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_conversacionOneToOne)
  }
}
