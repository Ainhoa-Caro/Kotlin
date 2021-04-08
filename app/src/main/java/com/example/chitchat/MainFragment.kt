package com.example.chitchat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class MainFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_registro, container, false)


        //guardado de datos
        val prefrencias = requireActivity().getSharedPreferences(getString(R.string.prefs_file),Context.MODE_PRIVATE).edit()
        prefrencias.putString("email",email)

        return  root
    }



}