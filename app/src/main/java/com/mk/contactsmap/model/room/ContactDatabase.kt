package com.mk.contactsmap.model.room

import android.content.Context
import androidx.room.*

@Database( entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase :RoomDatabase(){
    abstract val contactDao:ContactDao
    companion object{
        private var INSTANCE:ContactDatabase? = null
        fun getDatabase(context: Context):ContactDatabase{
            return synchronized(this){
                if(INSTANCE==null) Room.databaseBuilder(
                    context,
                    ContactDatabase::class.java,
                    "contact_database"
                ).fallbackToDestructiveMigration().build()
                else INSTANCE as ContactDatabase
            }
        }
    }
}

