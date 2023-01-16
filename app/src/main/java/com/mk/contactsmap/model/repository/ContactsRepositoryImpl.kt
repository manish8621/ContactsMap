package com.mk.contactsmap.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.ContactDatabase
import com.mk.contactsmap.model.room.Location
import com.mk.contactsmap.toContacts
import com.mk.peoplewebclient.ProfilesNetworkResponse
import com.mk.peoplewebclient.PeopleWebClient
import com.mk.peoplewebclient.Profile
import kotlinx.coroutines.flow.Flow

class ContactsRepositoryImpl(private val peopleWebClient: PeopleWebClient, private val database: ContactDatabase) :ContactsRepository(){

    /**
     *     will fetch data from api and inserts into database
     *     this function will return a boolean denoting if fetch is success or not
     */

    override suspend fun fetchProfiles():Boolean {

        var isFetchSuccess = true

        //fetch from api
        peopleWebClient.getPeopleProfiles(25).let {

            //check the response
            when(it){
                is ProfilesNetworkResponse.Success -> {

                    //convert into contacts
                    it.profiles.toContacts().let { contacts->

                        //clean the database
                        database.contactDao.deleteAllContacts()

                        //insert into database
                        database.contactDao.addContact( * contacts.toTypedArray() )

                    }
                }

                is ProfilesNetworkResponse.Error -> {
                    isFetchSuccess = false
                }
            }

        }

        return isFetchSuccess
    }

    override suspend fun getContacts(): Flow<List<Contact>> = database.contactDao.getContacts()

    //getting count
    override fun getContactsCount(gender:String): LiveData<Int> {
        return database.contactDao.getContactsCount(gender)
    }

    override fun getAllContactsCount(): LiveData<Int> {
        return database.contactDao.getAllContactsCount()
    }

    //add delete operations
    override fun deleteContact(contact: Contact) = database.contactDao.deleteContact(contact)

    override fun deleteAllContacts() = database.contactDao.deleteAllContacts()

    override fun addContact(vararg contact: Contact) = database.contactDao.addContact(* contact)
}