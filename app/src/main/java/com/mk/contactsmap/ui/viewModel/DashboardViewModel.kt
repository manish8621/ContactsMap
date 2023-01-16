package com.mk.contactsmap.ui.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mk.contactsmap.model.repository.ContactsRepository
import com.mk.contactsmap.model.room.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository: ContactsRepository):ViewModel() {

    val maleCount = repository.getContactsCount(Contact.MALE)
    val femaleCount = repository.getContactsCount(Contact.FEMALE)
    val totalCount = repository.getAllContactsCount()

init {

    }
}