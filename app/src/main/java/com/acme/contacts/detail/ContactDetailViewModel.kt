package com.acme.contacts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.acme.contacts.Contact
import com.acme.contacts.database.ContactsRepository

class ContactDetailViewModel(contactId: String?) : ViewModel() {

    private val contactsRepository = ContactsRepository.get()

    val contact : LiveData<Contact> = when(contactId) {
        null -> MutableLiveData(Contact())
        else -> contactsRepository.loadContact(contactId)
    }

    fun saveContact() {
        contactsRepository.saveContact(contact.value!!)
    }

    fun deleteContact() {
        contactsRepository.removeContact(contact.value!!)
    }

}