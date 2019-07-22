package com.acme.contacts.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import com.acme.contacts.FAVORITES_NOTIFICATION_CHANNEL_ID
import com.acme.contacts.R
import com.acme.contacts.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

}