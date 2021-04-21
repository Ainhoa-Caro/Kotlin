package com.example.chitchat.login

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


import androidx.fragment.app.Fragment as Fragment

class LoginAdapter(fm : FragmentManager,context:Context,totalTabs : Int): FragmentPagerAdapter(fm) {

    private lateinit var context : Context


    fun LoginAdapter(fm : FragmentManager, context:Context, totalTabs : Int ){
        this.context=context

    }

    override fun getCount(): Int = 2


    override fun getItem(position: Int): Fragment {
       when (position) {
           0 -> {
               val loginTabFragment = LoginTabFragment()
               return loginTabFragment
           }
           1 -> {
               val registroTabFragment = RegistroFragment()
               return registroTabFragment
           }
           else -> return null!!

       }
   }


}