package com.java.contacts.list

import androidx.lifecycle.ViewModel
import com.java.contacts.database.ContactsRepository

class ContactListViewModel(favoriteOnly: Boolean) : ViewModel() {

    private val contactsRepository = ContactsRepository.get()

    val contacts =  when {
        favoriteOnly -> contactsRepository.fetchFavoriteContacts()
        else -> contactsRepository.fetchAllContacts()
    }

}