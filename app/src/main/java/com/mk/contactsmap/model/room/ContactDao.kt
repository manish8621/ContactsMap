package com.mk.contactsmap.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ContactDao {
    @Query("select * from contacts_table")
    fun getContacts(): LiveData<List<Contact>>
    @Insert
    fun addContact(contact: Contact)
    @Delete
    fun deleteContact(contact: Contact)
}