package com.java.contacts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.contacts.Contact
import com.java.contacts.database.ContactsRepository

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
        contactsRepository.removeContact(contact.value!!.id)
    }

}