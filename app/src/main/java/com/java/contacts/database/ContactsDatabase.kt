package com.java.contacts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.java.contacts.Contact

@Database(entities = [ Contact::class ], exportSchema = false, version = 1 )
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

}