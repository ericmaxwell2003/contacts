package com.acme.contacts.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.acme.contacts.Contact
import java.util.concurrent.Executors

class ContactsRepository private constructor(context: Context) {

    /**
     * Public functions
     */
    fun fetchAllContacts() = contactsDao.fetchAllContacts()

    fun fetchFavoriteContacts() = contactsDao.fetchFavoriteContacts()

    fun loadContact(id: String) = contactsDao.loadContact(id)

    fun saveContact(contact: Contact) {
        // Simple example app, no error handling here.
        executor.execute { contactsDao.saveContact(contact) }
    }

    fun removeContact(contact: Contact) {
        // Simple example app, no error handling here.
        executor.execute { contactsDao.removeContact(contact) }
    }




    /**
     * Configuration...
     */
    private val contactsDatabase = Room.databaseBuilder(
        context.applicationContext, ContactsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    private val contactsDao = contactsDatabase.contactDao()
    private val executor = Executors.newSingleThreadExecutor()

    companion object {

        private const val DATABASE_NAME = "contacts_db"

        private var INSTANCE: ContactsRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    ContactsRepository(context)
            }
        }

        fun get(): ContactsRepository {
            return INSTANCE ?:
            throw IllegalStateException("ContactsRepository must be initialized")
        }
    }
}
