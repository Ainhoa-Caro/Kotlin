package com.example.chitchat.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment
import com.example.chitchat.R
import androidx.preference.*


class SettingsPreferencesChats : PreferenceFragmentCompat() {

    private lateinit var imageButtonRetroceder: ImageButton

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences_chats, rootKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_settings_chats, container, false)
        imageButtonRetroceder = root.findViewById<ImageButton>(R.id.imageButtonRetrocederChats)
        imageButtonRetroceder.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }





        return root
    }
}
