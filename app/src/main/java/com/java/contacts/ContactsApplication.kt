package com.java.contacts

import android.app.Application
import com.java.contacts.database.ContactsRepository

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContactsRepository.initialize(this)
    }

}