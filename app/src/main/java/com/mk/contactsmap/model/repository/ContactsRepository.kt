package com.mk.contactsmap.model.repository

import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.ContactDatabase

class ContactsRepository(private val database: ContactDatabase) {
    fun getContacts()= database.contactDao.getContacts()
    fun deleteContact(contact: Contact)= database.contactDao.deleteContact(contact)
    fun addContact(contact: Contact) = database.contactDao.addContact(contact)
}