package com.mk.contactsmap.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.contactsmap.model.repository.ContactsRepository
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.ContactDatabase
import com.mk.contactsmap.ui.Events
import com.mk.contactsmap.ui.UiState
import dagger.Module
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class MainViewModel@Inject constructor(private val repository: ContactsRepository) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateFlow :StateFlow<UiState>
    get() = _uiStateFlow.asStateFlow()

    val contactslist = repository.getContacts()

    fun handleEvent(event:Events){
        when(event){
            is Events.ContactCreate -> addContact(event.contact)
            is Events.ContactDelete -> deleteContact(event.contact)
        }
    }

    private fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO){
            repository.addContact(contact)
        }
    }
    private fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteContact(contact)
        }
    }
}

