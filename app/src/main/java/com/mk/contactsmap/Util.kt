package com.mk.contactsmap

import com.mk.contactsmap.model.room.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun String?.isValid() = (this!=null && (isNotBlank() && isNotEmpty()))
fun isValid(vararg data:String) = (data.count{ it.isValid().not() } == 0)
fun isValid(contact: Contact) = (contact.name.isValid() && contact.number.isValid() && contact.email.isValid())