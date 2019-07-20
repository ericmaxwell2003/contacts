package com.java.contacts.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.java.contacts.R
import com.java.contacts.databinding.FragmentContactDetailBinding

class ContactDetailFragment : Fragment() {

    val args by navArgs<ContactDetailFragmentArgs>()
    val contactDetailVm by viewModels<ContactDetailViewModel> { viewModelFactory }

    lateinit var binding: FragmentContactDetailBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_contact_detail, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.contact = contactDetailVm.contact
        binding.mode = Mode.NEW
        binding.deleteContactBtn.setOnClickListener { onDeleteContact() }
    }

    override fun onStop() {
        super.onStop()
        contactDetailVm.saveContact()
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