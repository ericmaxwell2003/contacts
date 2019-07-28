package com.acme.contacts

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.acme.contacts.database.ContactsRepository

const val FAVORITES_NOTIFICATION_CHANNEL_ID = "favorites_count"

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ContactsRepository.initialize(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_favorites_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(FAVORITES_NOTIFICATION_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager =
                getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

    }

}