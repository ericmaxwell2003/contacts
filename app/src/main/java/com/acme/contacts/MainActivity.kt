package com.acme.contacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.acme.contacts.detail.ContactDetailFragment
import com.acme.contacts.list.ContactsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
   }

}
