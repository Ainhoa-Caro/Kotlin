package com.example.chitchat.ui.ajustes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.chitchat.R

class AjustesFragment : Fragment(){
    private lateinit var ajustesViewModel: AjustesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        ajustesViewModel =
                ViewModelProviders.of(this).get(AjustesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_ajustes, container, false)
        val textView: TextView = root.findViewById(R.id.text_UserName)
        ajustesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}