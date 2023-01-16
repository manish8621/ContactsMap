package com.mk.contactsmap.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.contactsmap.model.INDIA_TELEPHONE_CODE
import com.mk.contactsmap.model.repository.ContactsRepository
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.ui.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddContactsViewModel @Inject constructor(private val repository: ContactsRepository):ViewModel() {
    var photo = MutableStateFlow("")
    var name = MutableStateFlow("")
    var countryCode = MutableStateFlow(INDIA_TELEPHONE_CODE)
    var number = MutableStateFlow("")
    var mail = MutableStateFlow("")

    fun setName(name:String) {
        this.name.value = name
    }
    fun setNumber(number:String) {
        this.number.value = number
    }
    fun setCountryCode(code:String) {
        this.countryCode.value = code
    }
    fun setMail(mail:String) {
        this.mail.value = mail
    }
    fun setPhotoPath(photoPath:String) {
        this.photo.value = photoPath
    }

    fun handleEvent(event: Event){
        when(event){
            is Event.ContactCreate -> addContact(Contact(name = name.value,
                number = countryCode.value+number.value,
                email = mail.value,
                photoPath = photo.value,
                location = event.location)
            )
            else -> {}
        }
    }

    private fun addContact(vararg contact: Contact) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addContact(*contact)
        }
    }
}