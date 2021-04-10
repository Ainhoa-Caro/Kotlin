package com.example.chitchat.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceFragmentCompat
import com.example.chitchat.R

class SettingsPreferencesProfile : PreferenceFragmentCompat() {

    private lateinit var imageButtonRetroceder: ImageButton

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences_profile, rootKey)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_settings_profile, container, false)
        imageButtonRetroceder = root.findViewById<ImageButton>(R.id.imageButtonRetrocederProfile)
        imageButtonRetroceder.setOnClickListener {
            NavHostFragment.findNavController(this@SettingsPreferencesProfile).navigate(R.id.action_fragment_settings_profile_to_settingsFragment2)
        }

        return root
    }
}