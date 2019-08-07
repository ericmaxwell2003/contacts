package com.acme.contacts.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.acme.contacts.Contact
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class ContactsRepository private constructor(context: Context, testMode: Boolean = false) {

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
    private val contactsDatabase: ContactsDatabase
    private val contactsDao: ContactDao
    private val executor: Executor

    init {
        contactsDatabase = if (testMode) {
            Room.inMemoryDatabaseBuilder(
                context.applicationContext, ContactsDatabase::class.java)
                .build()
        } else {
            Room.databaseBuilder(
                context.applicationContext, ContactsDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
        contactsDao = contactsDatabase.contactDao()
        executor = Executors.newSingleThreadExecutor()
    }

    companion object {

        private const val DATABASE_NAME = "contacts_db"

        private var INSTANCE: ContactsRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE =
                    ContactsRepository(context)
            }
        }

        /**
         * Initialize this repository for testing, using an in memory Room database instance.
         */
        fun setupForTesting(context: Context) {
            INSTANCE = ContactsRepository(context, testMode = true)
        }

        fun get(): ContactsRepository {
            return INSTANCE ?:
            throw IllegalStateException("ContactsRepository must be initialized")
        }
    }
}
