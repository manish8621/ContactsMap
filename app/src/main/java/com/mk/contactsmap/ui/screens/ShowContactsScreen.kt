package com.mk.contactsmap.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.mk.contactsmap.R
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.ui.Events
import com.mk.contactsmap.ui.UiState
import com.mk.contactsmap.ui.viewModel.MainViewModel
import java.io.File

@Composable
fun ShowContactsScreen(navController: NavHostController, viewModel: MainViewModel) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    val contacts by viewModel.contactslist.observeAsState(listOf())

    Box( contentAlignment = Alignment.Center, modifier = Modifier.padding(16.dp)) {

        Column(modifier = Modifier.fillMaxSize()) {
            CustomAppBar("All contacts")
            ContactsList(contacts){
                viewModel.handleEvent(Events.ContactDelete(it))
            }
        }

        //to show if no contacts
        if (uiState is UiState.Idle && contacts.isEmpty())
            Text(text = "No contacts", style = MaterialTheme.typography.labelMedium)

    }
}

@Composable
fun ContactsList(contacts:List<Contact>,onItemClicked:(contact:Contact)->Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        items(items = contacts) { contact ->
                ContactItem(contact){
                    onItemClicked(contact)
                }
        }
    }
}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ContactItem(contact: Contact,onClick:()->Unit) {

    val imagePainter = if(contact.photoPath.isNullOrEmpty())
        painterResource(R.drawable.person_24)
    else {
        Log.i("TAG", "ContactItem: ${File(contact.photoPath).exists()}")
        rememberAsyncImagePainter(contact.photoPath)
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

            Image(
                painter = imagePainter,
                contentDescription = "contact photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .width(40.dp)
                    .height(40.dp)
                    .clickable { onClick() }
            )

        Spacer(modifier = Modifier.width(16.dp))
        Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
    }
}