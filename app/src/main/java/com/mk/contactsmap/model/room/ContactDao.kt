package com.mk.contactsmap.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ContactDao {
    @Query("select * from contacts_table ORDER BY name")
    fun getContacts(): Flow<List<Contact>>

    @Query("select COUNT(*) from contacts_table where gender like :gender")
    fun getContactsCount(gender:String=""): LiveData<Int>

    @Query("select COUNT(*) from contacts_table")
    fun getAllContactsCount(): LiveData<Int>

    @Insert
    fun addContact(vararg contact: Contact)
    @Delete
    fun deleteContact(contact: Contact)
    @Query("delete from contacts_table")
    fun deleteAllContacts()
}