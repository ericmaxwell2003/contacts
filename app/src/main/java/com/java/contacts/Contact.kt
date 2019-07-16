package com.java.contacts

import java.util.*

data class Contact(
    val id: String = UUID.randomUUID().toString(),
    var name: String = "",
    var phone: String = "",
    var isFavorite: Boolean = false)