package com.mk.contactsmap.ui

import com.mk.contactsmap.model.room.Contact

sealed class Events {
    class ContactCreate(val contact: Contact):Events()
    class ContactDelete(val contact: Contact):Events()
}