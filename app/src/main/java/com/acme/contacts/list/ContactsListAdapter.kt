package com.acme.contacts.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.acme.contacts.Contact
import com.acme.contacts.R
import com.acme.contacts.databinding.ContactItemBinding

typealias ContactItemClickHandler = (contact: Contact) -> Unit
typealias ToggleFavoriteStatus = (contact: Contact) -> Unit

class ContactsListAdapter(
    private val onClickItem: ContactItemClickHandler = {},
    private val onToggleFavoriteStatus: ToggleFavoriteStatus = {}
) : ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = DataBindingUtil.inflate<ContactItemBinding>(
            LayoutInflater.from(parent.context), R.layout.contact_item, parent, false)
        return ContactViewHolder(binding, onClickItem, onToggleFavoriteStatus)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int)  = holder.bind(getItem(position))
}

class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact) = (oldItem.id == newItem.id)
    override fun areContentsTheSame(oldItem: Contact, newItem: Contact) = (oldItem == newItem)
}

class ContactViewHolder(
    private val itemBinding: ContactItemBinding,
    private val onClickItem: ContactItemClickHandler,
    private val onToggleFavoriteStatus: ToggleFavoriteStatus
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var contact: Contact

    init {
        itemBinding.contactNameTv.setOnClickListener {
            onClickItem(contact)
        }
        itemBinding.contactNameCb.setOnCheckedChangeListener { button, _ ->
            if(button.isPressed) {
                onToggleFavoriteStatus(contact)
            }
        }
    }

    fun bind(contact: Contact) {
        this.contact = contact
        itemBinding.contactNameTv.text = contact.name
        itemBinding.contactNameCb.isChecked = contact.isFavorite
        itemBinding.executePendingBindings()
    }

}

