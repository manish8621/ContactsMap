@file:OptIn(ExperimentalMaterial3Api::class)

package com.mk.contactsmap.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.mk.contactsmap.MainActivity
import com.mk.contactsmap.R
import com.mk.contactsmap.customComposables.BorderLessCurvedTextField
import com.mk.contactsmap.customComposables.CountryCodeSelector
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.isValid
import com.mk.contactsmap.model.LAT_KEY
import com.mk.contactsmap.model.LNG_KEY
import com.mk.contactsmap.model.room.Location
import com.mk.contactsmap.toast
import com.mk.contactsmap.ui.Event
import com.mk.contactsmap.ui.viewModel.AddContactsViewModel
import java.io.File
import java.io.IOException


@Composable
fun AddContactScreen(navController: NavHostController, viewModel: AddContactsViewModel = hiltViewModel()) {
    val activity = (LocalContext.current as MainActivity)

    val photo =viewModel.photo.collectAsState()
    val name =viewModel.name.collectAsState()
    val countryCode =viewModel.countryCode.collectAsState()
    val number =viewModel.number.collectAsState()
    val mail =viewModel.mail.collectAsState()


    //result from map screen
    val latitude = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Double>(LAT_KEY)?.observeAsState()
    val longitude = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Double>(LNG_KEY)?.observeAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
        it?.let { srcUri->
            //create folder if not exists
            val destDir = activity.filesDir.path+"/photos"
            File(destDir).let { outputFolder->
                if(outputFolder.exists().not())
                    outputFolder.mkdir()
            }
            //create output file
            val src = File(srcUri.toString())
            val dest = File(destDir+"/"+src.name)

            //copy the content of the uri to app specific storage
            try{
                activity.contentResolver.openInputStream(it)?.use {  inputStream ->
                    dest.outputStream().use { outputStream->
                        inputStream.copyTo(outputStream)
                        //the uri of the copied file will be stored in database
                        viewModel.setPhotoPath( dest.path.toString())
                        Log.i("TAG", dest.path.toString())
                    }
                }
            }
            catch (e:NoSuchFileException){
                Toast.makeText(activity, "NoSuchFileException occurred, try again", Toast.LENGTH_SHORT).show()
            }
            catch (e:IOException){
                Toast.makeText(activity, "IOException occurred, try again", Toast.LENGTH_SHORT).show()
            }
        }
    })
    Scaffold(
            topBar = {
                CustomAppBar(title = "Add contact",
                    navController = navController,
                    onDoneClicked = {
                        //TODO:make it a separate function
                        //check if valid
                        if(isValid(name.value,number.value,mail.value)) {
                            var locationSelected:Location? =null
                            latitude?.value?.let { lat->
                                longitude?.value?.let { lng->
                                    locationSelected = Location("_",lat,lng)
                                }
                            }

                            viewModel.handleEvent(
                                Event.ContactCreate( location = locationSelected)
                            )

                            Toast.makeText(activity, "Contact saved", Toast.LENGTH_SHORT).show()
                            navController.navigateUp()
                        }
                        else
                            Toast.makeText(activity,"details required", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = if(photo.value.isNullOrEmpty())
                            painterResource(R.drawable.person_24)
                        else {
                            rememberAsyncImagePainter(photo.value)
                        },
                    contentDescription = "contact photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .width(150.dp)
                        .height(150.dp)
                        .clickable { imagePickerLauncher.launch("image/*") }
                )
            Spacer(modifier = Modifier.height(16.dp))
            BorderLessCurvedTextField(
                value = name.value,
                onValChange = {viewModel.setName(it) },
                placeholder = "name"
            )


            //for phone number
            Row(modifier = Modifier.fillMaxWidth()
            ){
                CountryCodeSelector(value = countryCode.value, onSelected = {
                    code-> viewModel.setCountryCode(code)
                })
                BorderLessCurvedTextField(
                    value = number.value,
                    onValChange = { viewModel.setNumber(it) },
                    placeholder = "Phone",
                    keyboardType = KeyboardType.Number
                )
            }

            BorderLessCurvedTextField(value = mail.value
                , onValChange = {viewModel.setMail(it)}
                , placeholder = "Email"
                , keyboardType = KeyboardType.Email)

            BorderLessCurvedTextField(value = getLocationInfo(latitude?.value,longitude?.value)
                , onValChange = {}
                , placeholder = "Location"
                , leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn, tint = MaterialTheme.colorScheme.onSurface, contentDescription = "location icon"
                    )
                }
                ,enabled =false
                , onClick = {
                navController.navigate(Screen.SelectLocationScreen.route)
            })
        }
    }
}

fun getLocationInfo(lat:Double?,lng:Double?):String{
    val sb = StringBuilder("")
    if(lat!=null && lng!=null){
        sb.append("lat : %.2f lng : %.2f".format(lat,lng))
    }
    return sb.toString()
}

//
//@Preview(showBackground = true)
//@Composable
//fun AddContactPrev() {
//    AddContactScreen(rememberNavController(), LocalContext as MainActivityviewModel)
//}