package com.acme.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acme.contacts.detail.ContactDetailFragment
import com.acme.contacts.list.ContactsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.nav_host, ContactsListFragment())
                .commit()
        }
    }

    fun navToContactDetails(contactId: String? = null) {

        val contactDetailFragment = ContactDetailFragment().apply {
            arguments = Bundle().apply {
                putString("contactId", contactId)
            }
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host, contactDetailFragment)
            .addToBackStack(null)
            .commit()
    }

}
