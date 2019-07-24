package com.acme.contacts

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.acme.contacts.database.ContactsRepository

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContactsRepository.initialize(this)
    }

}