package com.java.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.contacts.databinding.FragmentContactsListBinding
import androidx.recyclerview.widget.DividerItemDecoration



class ContactsListFragment : Fragment() {

    lateinit var binding: FragmentContactsListBinding
    lateinit var adapter: ContactsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contacts_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        adapter = ContactsListAdapter()

        val rv = binding.rvContactsList
        rv.layoutManager = LinearLayoutManager(context)
        rv.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rv.adapter = adapter

        adapter.contacts = listOf("Bob", "Sheila", "Susan").map { Contact(name = it) }
    }

}