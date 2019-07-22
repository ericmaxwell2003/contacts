package com.acme.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Contact(
    @PrimaryKey
    var id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var phone: String = "",
    var isFavorite: Boolean = false)