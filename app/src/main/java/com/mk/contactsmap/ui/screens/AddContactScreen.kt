package com.mk.contactsmap.ui.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.mk.contactsmap.MainActivity
import com.mk.contactsmap.R
import com.mk.contactsmap.customComposables.BorderLessCurvedTextField
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.isValid
import com.mk.contactsmap.model.LAT_KEY
import com.mk.contactsmap.model.LNG_KEY
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.Location
import com.mk.contactsmap.ui.Events
import com.mk.contactsmap.ui.viewModel.MainViewModel
import java.io.File
import java.io.IOException
import kotlin.io.copyTo


@OptIn(ExperimentalCoilApi::class)
@Composable
fun AddContactScreen(navController: NavHostController, viewModel: MainViewModel) {
    val activity = (LocalContext.current as MainActivity)

    //TODO:Optimize
    var photo by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    var number by rememberSaveable {
        mutableStateOf("")
    }
    var mail by rememberSaveable {
        mutableStateOf("")
    }
    var location by rememberSaveable {
        mutableStateOf("")
    }

    //result from map screen
    val latitude = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Double>(LAT_KEY)?.observeAsState()
    val longitude = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Double>(LNG_KEY)?.observeAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(), onResult = {
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
                        photo = ( dest.path.toString())
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

    Column(modifier = Modifier.fillMaxSize()){
        CustomAppBar(title = "Add contact", onDoneClicked = {
            //check if valid
            if(isValid(name,number,mail)) {

                var locationSelected:Location? =null
                latitude?.value?.let { lat->
                    longitude?.value?.let { lng->
                        locationSelected = Location("_",lat,lng)
                    }
                }

                viewModel.handleEvent(
                    Events.ContactCreate(
                        Contact(name = name, number = number, email = mail, photoPath = photo
                            , location = locationSelected)
                    )
                )

                Toast.makeText(activity, "Contact saved", Toast.LENGTH_SHORT).show()
                navController.navigateUp()
            }
            else
                Toast.makeText(activity,"details required", Toast.LENGTH_SHORT).show()
        }
        , onCancelClicked = { navController.navigateUp()}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
                Image(
                    painter = if(photo.isNotBlank())
                        rememberAsyncImagePainter(photo)
                    else
                        painterResource(R.drawable.person_24),
                    contentDescription = "contact photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.background)
                        .width(150.dp)
                        .height(150.dp)
                        .clickable { imagePickerLauncher.launch("image/*") }

                )

            Spacer(modifier = Modifier.height(16.dp))

            BorderLessCurvedTextField(value = name, onValChange = {name = it}, placeholder = "name")
            BorderLessCurvedTextField(value = number?:""
                , onValChange = {number = it}
                , placeholder = "Phone"
                , keyboardType = KeyboardType.Number)
            BorderLessCurvedTextField(value = mail?:""
                , onValChange = {mail = it}
                , placeholder = "Email"
                , keyboardType = KeyboardType.Email)


            //TODO:change
            OutlinedTextField(value = getLocationInfo(latitude?.value,longitude?.value),
                onValueChange = {},
                shape= RoundedCornerShape(40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(Screen.SelectLocationScreen.route)
                    },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colorScheme.background,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                enabled = false,
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.LocationOn, tint = MaterialTheme.colorScheme.onSurface, contentDescription = "location icon"
                    )
                },
                placeholder = { Text(text = "Location") }
            )
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