package com.java.contacts.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.java.contacts.Contact
import com.java.contacts.R
import com.java.contacts.databinding.ContactItemBinding

typealias ContactItemClickHandler = (contact: Contact) -> Unit

class ContactsListAdapter(val onClickItem: ContactItemClickHandler = {}): ListAdapter<Contact, ContactViewHolder>(
    ContactDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = DataBindingUtil.inflate<ContactItemBinding>(
            LayoutInflater.from(parent.context), R.layout.contact_item, parent, false)
        return ContactViewHolder(binding, onClickItem)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int)  = holder.bind(getItem(position))
}

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact) = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact) = (oldItem == newItem)
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

