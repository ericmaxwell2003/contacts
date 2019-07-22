package com.acme.contacts.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.acme.contacts.Contact

@Dao
interface ContactDao {

    @Query("select * from contact order by name")
    fun fetchAllContacts(): LiveData<List<Contact>>

    @Query("select * from contact where isFavorite = 1 order by name")
    fun fetchFavoriteContacts(): LiveData<List<Contact>>

    @Query("select * from contact where id=:id")
    fun loadContact(id: String): LiveData<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContact(contact: Contact)

    @Delete
    fun removeContact(contact: Contact)

}