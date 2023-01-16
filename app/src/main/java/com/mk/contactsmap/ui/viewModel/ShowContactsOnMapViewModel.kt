package com.mk.contactsmap.ui.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowContactsOnMapViewModel @Inject constructor(private val repository: ContactsRepository) : ViewModel() {

    //flow data will start to emit data from database on init of view model
    //so initially showing loading state
    private val _uiState = mutableStateOf<UiState>(UiState.Loading)
    val uiState = _uiState as State<UiState>

    //data
    private var _contactsList = mutableStateOf<List<Contact>>(listOf())
    val contactsList = _contactsList as State<List<Contact>>



    init {

        //flow will be collected whenever there's a change in database
        viewModelScope.launch {

            repository.getContacts().collectLatest { contacts->

                //on every collect the state will be changed to normal
                setUiState(UiState.Idle)

                //set data
                setContactsList(contacts)

                //debg
                Log.i("TAG", " emission:$contacts ")
            }

        }
    }

    fun handleEvent(event: Event){
        when(event){
            is Event.ContactDelete -> deleteContact(event.contact)
            is Event.RefreshContacts -> {
                viewModelScope.launch(Dispatchers.IO) {

                    //changes ui state
                    setUiState(UiState.Refreshing)

                    //if api fetch was un success then move to error state
                    if(repository.fetchProfiles().not()){
                        setUiState(
                            UiState.Error("Error while fetching data from internet !")
                        )
                    }
                }
            }
            is Event.AllContactsDelete ->{
                //clears the state
                clearContactsList()
                //clears the database
                deleteAllContacts()
            }
        }
    }

    private fun setUiState(uiState: UiState){
        _uiState.value = uiState
    }
    private fun setContactsList(contacts: List<Contact>){
        _contactsList.value = contacts
    }
    private fun clearContactsList() {
        setContactsList(listOf())
    }

    private fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteContact(contact)
        }
    }
    private fun deleteAllContacts() {
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteAllContacts()
        }
    }
}

