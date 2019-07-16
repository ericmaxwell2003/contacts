package com.java.contacts

import android.database.DatabaseUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.contacts.databinding.ContactItemBinding

typealias ContactItemClickHandler = (contact: Contact) -> Unit

class ContactsListAdapter(val onClickItem: ContactItemClickHandler = {}): RecyclerView.Adapter<ContactViewHolder>() {

    var contacts: List<Contact> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = DataBindingUtil.inflate<ContactItemBinding>(
            LayoutInflater.from(parent.context),  R.layout.contact_item, parent, false)
        return ContactViewHolder(binding, onClickItem)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact)
    }

}


class ContactViewHolder(val itemBinding: ContactItemBinding, val onClickItem: ContactItemClickHandler) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var contact: Contact

    init {
        itemBinding.root.setOnClickListener {
            onClickItem(contact)
        }
    }

    fun bind(contact: Contact) {
        this.contact = contact
        itemBinding.contactNameCheckedTv.apply {
            text = contact.name
            isChecked = contact.isFavorite
            itemBinding.executePendingBindings()
        }
    }

}

