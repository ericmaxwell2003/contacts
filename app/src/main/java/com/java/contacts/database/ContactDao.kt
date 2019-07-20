package com.java.contacts.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.java.contacts.Contact

@Dao
interface ContactDao {

    @Query("select * from contact")
    fun fetchAllContacts(): LiveData<List<Contact>>

    @Query("select * from contact where isFavorite = 0")
    fun fetchFavoriteContacts(): LiveData<List<Contact>>

    @Query("select * from contact where id=:id")
    fun loadContact(id: String): LiveData<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveContact(contact: Contact)

    @Transaction
    @Query("delete from contact where id=:id")
    fun removeContact(id: String)

}