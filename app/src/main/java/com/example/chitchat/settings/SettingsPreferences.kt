package com.example.chitchat.settings

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.*
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.chitchat.R
import java.util.*



class SettingsPreferences : PreferenceFragmentCompat() {

    private lateinit var imageButtonRetroceder: ImageButton
    private lateinit var profilePreference: Preference
    private lateinit var accountPreference: Preference
    private lateinit var chatsPreference: Preference


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        //val root = inflater.inflate(R.layout.fragment_settings, container, false)
        //profile sin implementar
/*
        val preference = preferenceManager.findPreference<Preference>("messages")
        preference?.fragment = MessageSettingsFragment.javaclass.name.removeSuffix("\$Companion")


        profilePreference= findPreference("edit_text_preference_profile")!!

        profilePreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { v: Preference? ->
            findNavController(this@SettingsPreferences).navigate(R.id.action_settingsFragment_to_fragment_settings_profile)
            true
        }

        //Account
        accountPreference = findPreference("edit_text_preference_account")!!

        accountPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            NavHostFragment.findNavController(this@SettingsPreferences).navigate(R.id.action_settingsFragment_to_fragment_settings_account)
            true
        }

        //
        //chats sin implementar
         chatsPreference = findPreference<Preference>("edit_text_preference_chats")!!

        chatsPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { v: Preference? ->
            findNavController(this@SettingsPreferences).navigate(R.id.action_settingsFragment_to_fragment_settings_chats)
            true
        }
/*
        //notificaciones sin implementar
        val notificationPreference: Preference = findPreference<Preference>("edit_text_preference_notification")!!

        notificationPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { v: Preference? ->
            //findNavController(this).navigate(R.id.action_settingsFragment_to_settingsFragmentAccount)
            true
        }

        //language sin implementar
        val languagePreference: Preference = findPreference<Preference>("edit_text_preference_language")!!

        languagePreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { v: Preference? ->

            true
        }


        //ayuda sin implementar
        val helpPreference: Preference = findPreference<Preference>("edit_text_preference_help")!!

        helpPreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { v: Preference? ->
            //findNavController(this).navigate(R.id.action_settingsFragment_to_settingsFragmentAccount)
            true
        }

        //invitar sin implementar
        val invitePreference: Preference = findPreference<Preference>("edit_text_preference_invite")!!

        invitePreference.onPreferenceClickListener = Preference.OnPreferenceClickListener { v: Preference? ->
            //findNavController(this).navigate(R.id.action_settingsFragment_to_settingsFragmentAccount)
            true
        }




*/



*/

        return view
    }
}