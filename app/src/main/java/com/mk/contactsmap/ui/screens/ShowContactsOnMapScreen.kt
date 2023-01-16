package com.mk.contactsmap.ui.screens

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.size.Scale
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import coil.transform.Transformation
import coil.util.CoilUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.mk.contactsmap.ContextActions
import com.mk.contactsmap.ContextActions.MakeToast
import com.mk.contactsmap.R
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.customComposables.ErrorMessageText
import com.mk.contactsmap.customComposables.PhotoPreviewer
import com.mk.contactsmap.isValid
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.Location
import com.mk.contactsmap.ui.Event
import com.mk.contactsmap.ui.UiState
import com.mk.contactsmap.ui.viewModel.ShowContactsOnMapViewModel
import com.mk.contactsmap.using
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowContactsOnMapScreen(navController: NavHostController,viewModel: ShowContactsOnMapViewModel = hiltViewModel()) {
    val uiState by remember{ viewModel.uiState }
    val contacts by remember{ viewModel.contactsList }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState() {
        CameraPosition.fromLatLngZoom(LatLng(0.0,0.0),10F)
    }

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Expanded)
    )

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetPeekHeight = 46.dp,
        sheetShape = RoundedCornerShape(topEndPercent = 10, topStartPercent = 10),
        sheetElevation = 10.dp,
        sheetContent = {

            Column(modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
                .background(androidx.compose.material3.MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //indicator to swipe

                androidx.compose.material3.IconButton(onClick = {
                    handleBottomSheetState(coroutineScope,bottomSheetScaffoldState)
                }){
                    Icon(
                        imageVector = if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            Icons.Filled.KeyboardArrowUp
                        } else {
                            Icons.Default.KeyboardArrowDown
                        },
                        contentDescription = "bottom sheet handler"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))


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

                            androidx.compose.material3.Text(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 32.dp),
                                textAlign = TextAlign.Center,
                                text = "No contacts",
                                style = androidx.compose.material3.MaterialTheme.typography.labelMedium
                            )
                        }
                        else {
                            ContactsList(contacts,
                                showDivider = false,
                                showLocationStatus = true,
                                onItemClicked = { contact ->

                                    //move camera
                                    moveCamera(context,
                                        contact.location,
                                        coroutineScope,
                                        cameraPositionState)
                                }
                            )

                        }
                    }

                    else -> {}
                }
            }
        }
    ){

        Box(modifier = Modifier
            .fillMaxSize()
            .padding())
        {



            //ui will be rendered based on ui state
            when(uiState){

                is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                is UiState.Idle -> GoogleMap(
                    cameraPositionState = cameraPositionState,
                    modifier = Modifier.fillMaxSize(),
                    uiSettings = MapUiSettings(zoomControlsEnabled = false)
                ) {
                    contacts.forEach {
                        if (it.location != null) {
                            CustomMarker(context,coroutineScope,it)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

fun moveCamera(context: Context,
    location: Location?,
    coroutineScope: CoroutineScope,
    cameraPositionState: CameraPositionState
)
{
    location?.let{
        coroutineScope.launch{
            cameraPositionState.animate(
                update = CameraUpdateFactory.newCameraPosition(
                    CameraPosition(
                        LatLng(it.latitude, it.longitude),
                        12F,
                        0F,
                        0F
                    )
                ), 1000
            )
        }
    }?:run{
        MakeToast("user has no location") using context
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun handleBottomSheetState(coroutineScope: CoroutineScope, bottomSheetScaffoldState: BottomSheetScaffoldState) {
    coroutineScope.launch{
        with(bottomSheetScaffoldState.bottomSheetState){
            if (isCollapsed) {
                expand()
            } else {
                collapse()
            }
        }
    }
}

@Composable
fun CustomMarker(context:Context,
                 coroutineScope: CoroutineScope,
                 contact: Contact,

) {

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    contact.location?.let {  location->

        LaunchedEffect(key1 = null){

            //loads the profile picture
            bitmap = if (contact.photoPath.isValid()) {

                //load from internet or from file
                loadIcon(context = context, url = contact.photoPath)

            }
            else {

                //load default image
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.user_colored
                )

            }
        }

        //create marker when image was loaded successfully

        bitmap?.let{ bitmap->
            MarkerInfoWindow(
                state = MarkerState(
                    position = LatLng(
                        location.latitude,
                        location.longitude
                    )
                ),
                icon = BitmapDescriptorFactory.fromBitmap(bitmap)

            ) {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10))
                        .background(MaterialTheme.colors.background)
                        .padding(15.dp)

                ) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
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
                    Text(text = contact.name,
                        style = MaterialTheme.typography.h5
                    )
                    Text(
                        text = contact.number ?: "no_number",
                        style = MaterialTheme.typography.body1
                    )
                    Text(text = contact.email ?: "no_mail", style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}



suspend fun loadIcon(context: Context,
                     url:String?): Bitmap
{
    val imageLoader = ImageLoader(context)

    //build coil request object
    //scales the image as small and to circular shape
    val request = ImageRequest.Builder(context)
        .size(200,200)
        .scale(Scale.FILL)
        .error(R.drawable.user_colored)
        .transformations(CircleCropTransformation())
        .data(url)
        .build()

    val result = (imageLoader.execute(request) as SuccessResult).drawable
    return (result as BitmapDrawable).bitmap
}

