package com.java.contacts.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.contacts.databinding.FragmentContactsListBinding
import androidx.recyclerview.widget.DividerItemDecoration
import com.java.contacts.Contact
import com.java.contacts.R

class ContactsListFragment : Fragment() {

    private val contactsListVm by viewModels<ContactListViewModel>()

    lateinit var binding: FragmentContactsListBinding
    lateinit var adapter: ContactsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ContactsListAdapter(onClickItem = ::onContactItemClick)

        val rv = binding.rvContactsList
        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rv.adapter = adapter

        contactsListVm.contacts.observe(viewLifecycleOwner, Observer { allContacts ->
            allContacts?.let {
                adapter.submitList(allContacts)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController()) ||
           super.onOptionsItemSelected(item)
    }

    private fun onContactItemClick(contact: Contact) {
        findNavController().navigate(
            ContactsListFragmentDirections.toContactDetail(contactId = contact.id))
    }
}
