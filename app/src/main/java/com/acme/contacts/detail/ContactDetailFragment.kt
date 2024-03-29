package com.acme.contacts.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.acme.contacts.R
import com.acme.contacts.databinding.FragmentContactDetailBinding


class ContactDetailFragment : Fragment() {

    val contactDetailVm by viewModels<ContactDetailViewModel> { viewModelFactory }

    var contactId: String? = null
    lateinit var binding: FragmentContactDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactId = arguments?.getString("contactId")
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
            mode = if (contactId.isNullOrEmpty()) Mode.NEW else Mode.EDIT

            saveContactBtn.setOnClickListener { onSaveContact() }
            deleteContactBtn.setOnClickListener { onDeleteContact() }
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

    fun onSaveContact() {
        contactDetailVm.saveContact()
        requireActivity().supportFragmentManager.popBackStack()
    }

    fun onDeleteContact() {
        contactDetailVm.deleteContact()
        requireActivity().supportFragmentManager.popBackStack()
    }

    var viewModelFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContactDetailViewModel(contactId) as T
        }
    }
}