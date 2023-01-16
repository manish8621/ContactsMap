@file:OptIn(ExperimentalMaterial3Api::class)

package com.mk.contactsmap.ui.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mk.contactsmap.R
import com.mk.contactsmap.checkInternet
import com.mk.contactsmap.customComposables.*
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.toast
import com.mk.contactsmap.ui.Event
import com.mk.contactsmap.ui.UiState
import com.mk.contactsmap.ui.viewModel.ShowContactsViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ShowContactsScreen(navController: NavHostController, viewModel: ShowContactsViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState
    val contacts by viewModel.contactsList
    val context = LocalContext.current

    //to preview the photo
    var previewPhoto by remember {
        mutableStateOf("")
    }

    val pullRefreshState = rememberPullRefreshState(refreshing = uiState is UiState.Refreshing,
        onRefresh = { refreshContacts(context,viewModel) }
    )

    Scaffold(topBar = {
        CustomAppBar("All contacts",navController,
            onRefreshClicked = if(contacts.isEmpty()){
                { refreshContacts(context,viewModel) }
            }
            else
            {
                null
            }

            ,
            overFlowMenu = {
                OverFlowMenu { expanded ->
                    OverFlowMenuItem(text = "Delete all",
                        expanded = expanded,
                        onClick = {
                            viewModel.handleEvent(Event.AllContactsDelete)
                            context.toast("All contacts deleted !")
                    })
                }

            })
            },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddContactScreen.route)
            }
            , containerColor = MaterialTheme.colorScheme.primary
            ,contentColor = MaterialTheme.colorScheme.let {
                    it.contentColorFor(it.primary)
                }

            ) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "add contacts button")
            }
        }
    ){

        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pullRefresh(pullRefreshState)
        ) {

            //to preview profile photo
            if(previewPhoto.isNotEmpty()){
                PhotoPreviewer(photo = previewPhoto){

                    //make the previewPhoto empty to close the preview
                    previewPhoto = ""
                }
            }

            //render ui based on the ui state
            when(uiState){

                is UiState.Loading -> CircularProgressIndicator()

                is UiState.Error -> {
                    ErrorMessageText(
                        (uiState as UiState.Error).msg
                    )
                }

                is UiState.Idle -> {
                    //check list size is empty or not
                    if (contacts.isEmpty()) {
                        Text(text = "No contacts", style = MaterialTheme.typography.labelMedium)
                    }
                    else {
                        ContactsList(contacts,
                            onProfilePictureClicked = { photo ->
                                previewPhoto = photo ?: ""
                            },
                            onItemClicked = { contact ->
//                                viewModel.handleEvent(
//                                    Event.ContactDelete(contact)
//                                )
                            }
                        )
                    }
                }

                else -> {}
            }
            PullRefreshIndicator(refreshing = uiState is UiState.Refreshing ,
                state = pullRefreshState,
                modifier = Modifier.align(
                Alignment.TopCenter
            ))
        }
    }
}

fun refreshContacts(context: Context,viewModel: ShowContactsViewModel){
    if (context.checkInternet()) {
        viewModel.handleEvent(Event.RefreshContacts)
    } else {
        context.toast("no internet")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(contacts:List<Contact>,
                 showDivider:Boolean=true,
                 showLocationStatus:Boolean=true,
                 onProfilePictureClicked:(photo:String?)->Unit={},
                 onItemClicked:(contact:Contact)->Unit) {

    //group the contacts based on first letter of name
    val contactsGrouped = remember( key1 = contacts ){
        contacts.groupBy { it.name.first() }
    }

    //sort the keys( ex: a,b,c )
    val contactsSortedKeys = remember( key1 = contacts ){
        contactsGrouped.keys.sorted()
    }

    //content
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {

        contactsSortedKeys.forEach{ key ->

            //header
            item{
                ContactItemHeader(title = key.toString())
            }

            //list
            contactsGrouped[key]?.let {
                items(it){ contact ->
                    ContactItem(
                        contact,
                        showLocationStatus,
                        onProfilePictureClicked = onProfilePictureClicked) {
                            onItemClicked(contact)
                        }
                }

                //divider
                if(showDivider){
                    item {
                        Divider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }

        }
    }
}


@Composable
fun ContactItemHeader(title:String) {

    Text(text = title
        , modifier = Modifier
            .padding(top = 8.dp)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
        , color = MaterialTheme.colorScheme.secondary)
}
@Composable
fun ContactItem(
    contact: Contact,
    showLocationStatus: Boolean=false,
    onProfilePictureClicked: (photo: String?) -> Unit,
    onClick: () -> Unit
) {

    val imagePainter = if(contact.photoPath.isNullOrEmpty()) {
        painterResource(R.drawable.person_24)
    }
    else {
        rememberAsyncImagePainter(contact.photoPath)
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    )
    {

        //profile picture
        Image(
                painter = imagePainter,
                contentDescription = "contact photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
                    .width(40.dp)
                    .height(40.dp)
                    .clickable { onProfilePictureClicked(contact.photoPath) }
            )

        Spacer(modifier = Modifier.width(16.dp))

        //contact name
        Text(text = contact.name, style = MaterialTheme.typography.titleMedium)

        //

        Spacer(modifier = Modifier.weight(1f))

        if(showLocationStatus && contact.location==null){
            Icon(
                painter = painterResource(id = R.drawable.ic_unknown_location_24),
                contentDescription = "unknown location indicator")
        }
    }
}
