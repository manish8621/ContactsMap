package com.mk.contactsmap.model.repository

import androidx.lifecycle.LiveData
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.ContactDatabase
import com.mk.peoplewebclient.PeopleWebClient
import com.mk.peoplewebclient.Profile
import kotlinx.coroutines.flow.Flow

abstract class ContactsRepository {
    companion object{
        fun getInstance(peopleWebClient: PeopleWebClient,database: ContactDatabase):ContactsRepository = ContactsRepositoryImpl(
            peopleWebClient ,database)
    }

    abstract suspend fun getContacts(): Flow<List<Contact>>

    abstract fun getContactsCount(gender:String): LiveData<Int>
    abstract fun getAllContactsCount(): LiveData<Int>

    //will fetch data from api and inserts into database
    //this function will return a boolean denoting if fetch is success or not
    abstract suspend fun fetchProfiles(): Boolean

    abstract fun deleteContact(contact: Contact)

    abstract fun deleteAllContacts()

    abstract fun addContact(vararg contact: Contact)
}