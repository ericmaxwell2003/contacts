package com.acme.contacts.list

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.acme.contacts.databinding.FragmentContactsListBinding
import androidx.recyclerview.widget.DividerItemDecoration
import com.acme.contacts.Contact
import com.acme.contacts.R
import com.acme.contacts.security.SecureFragment

class ContactsListFragment : SecureFragment() {

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
        super.onViewCreated(view, savedInstanceState)
        adapter = ContactsListAdapter(
            onClickItem = ::onContactItemClick,
            onToggleFavoriteStatus = ::toggleContactFavoriteStatus
        )

        val rv = binding.rvContactsList
        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rv.adapter = adapter

        contactsListVm.contacts.observe(viewLifecycleOwner, Observer { contacts ->
            contacts?.let {
                adapter.submitList(contacts)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.contacts_list_option_menu, menu)

        val toggleFavorites = menu.findItem(R.id.toggle_favorites_only)

        val (toggleFavsTitle, toggleFavsIcon) = if (contactsListVm.showFavoriteOnlyCurrentValue) {
            arrayOf(R.string.title_show_all, R.drawable.ic_contact_favorite_selected_white)
        } else {
            arrayOf(R.string.title_favorites_only, R.drawable.ic_contact_favorite_unselected_white)
        }
        toggleFavorites.setTitle(toggleFavsTitle)
        toggleFavorites.setIcon(toggleFavsIcon)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, findNavController()) ||
           when(item.itemId) {
               R.id.toggle_favorites_only -> {
                   contactsListVm.toggleShowFavoritesOnly()
                   requireActivity().invalidateOptionsMenu()
                   true
               }
               else ->  super.onOptionsItemSelected(item)
           }
    }

    private fun onContactItemClick(contact: Contact) {
        findNavController().navigate(
            ContactsListFragmentDirections.toContactDetail(contactId = contact.id))
    }

    private fun toggleContactFavoriteStatus(contact: Contact) {
        contactsListVm.toggleContactFavoriteStatus(contact)
    }
}
