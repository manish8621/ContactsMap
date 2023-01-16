package com.mk.contactsmap.di

import android.content.Context
import com.mk.contactsmap.model.repository.ContactsRepository
import com.mk.contactsmap.model.repository.ContactsRepositoryImpl
import com.mk.contactsmap.model.room.ContactDatabase
import com.mk.peoplewebclient.PeopleWebClient
import dagger.Module
import dagger.Provides
import dagger.hilt.*
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun providesContactsRepository(@ApplicationContext context: Context):ContactsRepository{
        return ContactsRepository.getInstance(
            PeopleWebClient.getInstance(),
            ContactDatabase.getDatabase(context)
        )
    }
}