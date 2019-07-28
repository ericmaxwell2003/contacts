package com.acme.contacts.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.acme.contacts.FAVORITES_NOTIFICATION_CHANNEL_ID
import com.acme.contacts.R
import com.acme.contacts.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {

    val args by navArgs<ContactDetailFragmentArgs>()
    val contactDetailVm by viewModels<ContactDetailViewModel> { viewModelFactory }

    lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(args.contactId != null) {
            setHasOptionsMenu(true)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            contact = contactDetailVm.contact
            mode = if (args.contactId.isNullOrEmpty()) Mode.NEW else Mode.EDIT

            saveContactBtn.setOnClickListener { onSaveContact() }
            deleteContactBtn.setOnClickListener { onDeleteContact() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.contact_details_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController()) ||
                when(item.itemId) {
                    R.id.simulate_notification -> {
                        simulateNotification()
                        true
                    }
                    else ->  super.onOptionsItemSelected(item)
                }
    }

    override fun onStop() {
        super.onStop()

        requireActivity().currentFocus?.let {
            val inputManager = requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(it.windowToken, HIDE_NOT_ALWAYS)
        }
    }

    fun simulateNotification() {
        Toast.makeText(context, "Simulate Deep Link", Toast.LENGTH_SHORT).show()

        val pendingIntent = findNavController().createDeepLink()
            .setDestination(R.id.contact_detail)
            .setArguments(arguments)
            .createPendingIntent()

        val notification = NotificationCompat
            .Builder(requireContext(), FAVORITES_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.encouragement_message_title))
            .setSmallIcon(R.drawable.ic_contact_favorite_selected)
            .setContentText(getString(R.string.encouragement_message_description,
                                contactDetailVm.contact.value?.name ?: ""))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(requireContext())
        notificationManager.notify(0, notification)
    }

    fun onSaveContact() {
        contactDetailVm.saveContact()
        findNavController().popBackStack()
    }

    fun onDeleteContact() {
        contactDetailVm.deleteContact()
        findNavController().popBackStack()
    }

    var viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContactDetailViewModel(args.contactId) as T
        }
    }
}