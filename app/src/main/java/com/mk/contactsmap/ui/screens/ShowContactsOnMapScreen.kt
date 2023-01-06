package com.mk.contactsmap.ui.screens

import android.graphics.*
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.*
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.mk.contactsmap.R
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.isValid
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.ui.viewModel.MainViewModel
import java.io.File

@Composable
fun ShowContactsOnMapScreen(navController: NavHostController,viewModel: MainViewModel= hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()){
        CustomAppBar(title ="Contacts on map",navController)
        val contacts by viewModel.contactslist.observeAsState(initial = listOf())
        GoogleMap(modifier = Modifier.fillMaxSize(), uiSettings = MapUiSettings(zoomControlsEnabled = true)){
            contacts.forEach{
                if(it.location!=null) CustomMarker(it)
            }
        }
    }
}

@Composable
fun CustomMarker(contact: Contact) {
    contact.location?.let {  location->

        val bitmapIcon = if(contact.photoPath.isValid()) {
            BitmapFactory.decodeFile(contact.photoPath)
        }
        else {
            BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.user_colored)
        }

        MarkerInfoWindow(
                state = rememberMarkerState(position = LatLng(location.latitude,location.longitude))
            , icon = BitmapDescriptorFactory.fromBitmap(bitmapIcon.resize(200,200).asCircledBitmap())
            ){
                Column(modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .background(MaterialTheme.colors.background)
                    .padding(8.dp)

                ) {
                    Image(
                        bitmap = bitmapIcon.asImageBitmap(),
                        contentDescription = "contact photo",
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
                            .width(150.dp)
                            .height(150.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = contact.name, style = MaterialTheme.typography.h5)
                    Text(text = contact.number?:"no_number", style = MaterialTheme.typography.body1)
                    Text(text = contact.email?:"no_mail", style = MaterialTheme.typography.body1)
                }
            }
        }
}

private fun Bitmap.resize(maxHeight:Int,maxWidth:Int) :Bitmap{
    val ratioBitmap = (this.width.toFloat())/(this.height.toFloat())
    val ratioMax = (maxWidth.toFloat())/(maxHeight.toFloat())

    var finalWidth = maxWidth
    var finalHeight = maxHeight

    if(ratioMax>ratioBitmap){
        finalWidth = (maxHeight.toFloat() * ratioBitmap).toInt()
    }
    else{
        finalWidth = (maxWidth.toFloat() / ratioBitmap).toInt()
    }

    return Bitmap.createScaledBitmap(this, finalWidth, finalHeight, false)
}

private fun Bitmap.asCircledBitmap(): Bitmap {
    val output = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)
    val paint = Paint()
    val rect = Rect(0, 0, this.width, this.height)
    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawCircle(this.width / 2f, this.height / 2f, this.width / 2f, paint)
    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)
    return output
}