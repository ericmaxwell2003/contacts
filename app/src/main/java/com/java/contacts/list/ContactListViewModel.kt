package com.java.contacts.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.java.contacts.database.ContactsRepository

class ContactListViewModel() : ViewModel() {

    private val contactsRepository = ContactsRepository.get()

    private val showFavoriteOnly = MutableLiveData<Boolean>(false)

    val contacts = Transformations.switchMap(showFavoriteOnly) { favoriteOnly ->
        when {
            favoriteOnly -> contactsRepository.fetchFavoriteContacts()
            else -> contactsRepository.fetchAllContacts()
        }
    }

    val showFavoriteOnlyCurrentValue get() = showFavoriteOnly.value!!

    fun toggleFavoriteOnly() {
        showFavoriteOnly.value = !showFavoriteOnlyCurrentValue
    }
}