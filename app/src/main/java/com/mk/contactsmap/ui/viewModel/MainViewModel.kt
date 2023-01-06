package com.mk.contactsmap.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.contactsmap.model.repository.ContactsRepository
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.ui.Event
import com.mk.contactsmap.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ContactsRepository) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Idle)
    val uiStateFlow :StateFlow<UiState>
    get() = _uiStateFlow.asStateFlow()

    val contactslist = repository.getContacts()

    fun handleEvent(event:Event){
        when(event){
            is Event.ContactDelete -> deleteContact(event.contact)
        }
    }

    private fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteContact(contact)
        }
    }
}

