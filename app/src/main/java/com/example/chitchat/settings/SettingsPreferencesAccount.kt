package com.example.chitchat.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chitchat.R


class SettingsPreferencesAccount : PreferenceFragmentCompat() {

    private lateinit var imageButtonRetroceder: ImageButton

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences_account, rootKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_settings_account, container, false)
        imageButtonRetroceder = root.findViewById(R.id.imageButtonRetrocederAccount)
        imageButtonRetroceder.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }

        return root
    }
}
