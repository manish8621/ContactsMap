package com.mk.contactsmap.ui.screens

import android.location.Geocoder
import android.widget.SearchView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.mk.contactsmap.*
import com.mk.contactsmap.R
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.customComposables.SearchBox
import com.mk.contactsmap.model.LAT_KEY
import com.mk.contactsmap.model.LNG_KEY
import com.mk.contactsmap.ui.Event
import com.mk.contactsmap.ui.UiState
import com.mk.contactsmap.ui.UiState.Loading
import com.mk.contactsmap.ui.viewModel.SelectLocationViewModel
import com.mk.contactsmap.util.LocationClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SelectLocationScreen(navController: NavHostController, viewModel: SelectLocationViewModel = viewModel()){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope { Dispatchers.IO }

    val uiState by viewModel.uiState.collectAsState()
    val locationSelected by viewModel.locationSelected.collectAsState()
    val locationSearchQuery by viewModel.locationSearchQuery.collectAsState()
    val locationClient = remember {
        LocationServices.getFusedLocationProviderClient(context).let {
            LocationClient(it)
        }
    }
    val geoCoder = remember() {
        Geocoder(context)
    }
    val cameraPositionState = rememberCameraPositionState() {
        CameraPosition.fromLatLngZoom(locationSelected,12F)
    }
    val permissionState = rememberPermissionState(permission = LocationClient.LOCATION_PERMISSION){
        status ->
            if(status)
            {
                if(!LocationClient.checkLocationEnabled(context)) {
                    context.toast("turn on the location")
                }
                else
                {
                    context.toast("getting location")
                    locationClient.getCurrentLocationUpdates(
                        context = context,
                        oneShot = true
                    ) {
                        val event = Event.LocationSelect(it.toLatLng())
                        viewModel.handleEvent(event)
                    }
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CustomAppBar(title = "select a location"
            ,navController=navController
            ,onDoneClicked = null)


        Box(modifier = Modifier.fillMaxSize()){
            //zoom animation
            if(locationSelected.validLatLng())
                LaunchedEffect(key1 = locationSelected, block = {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newCameraPosition(
                            CameraPosition(locationSelected,12F,0F,0F)
                        )
                        ,1000
                    )
                })
            //search box
            //show only if geocoder is present in device
            if(Geocoder.isPresent())
                SearchBox(
                    value = locationSearchQuery,
                    onValueChange = { viewModel.setLocationSearchQuery(it) },
                    placeholder = "Search location",
                    isLoading = uiState is Loading,
                    onClearClicked = { viewModel.clearLocationSearchQuery() },
                    onSearch = {
                            if(locationSearchQuery.isValid().not()){
                                return@SearchBox
                            }
                            if(context.checkInternet().not()){
                                context.toast("no internet")
                                return@SearchBox
                            }
                            coroutineScope.launch{
                                viewModel.handleEvent(event = Event.LoadingStarted)
                                geoLocate(geoCoder, locationSearchQuery)?.let {
                                    //on success
                                    viewModel.selectLocation(it)
                                } ?: run {
                                    //on Not found
                                    launch(Dispatchers.Main){ context.toast("location not found") }
                                }
                                viewModel.handleEvent(event = Event.LoadingEnded)
                            }
                    }
                )

            //map
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
                ,
                uiSettings = MapUiSettings(zoomControlsEnabled = false),
                onMapClick = {
                    viewModel.locationSelected.value = it
                }
            ) {
                //mark only if user selected a location
                if (locationSelected.validLatLng()) {
                    Marker(
                        state = MarkerState(locationSelected),
                        title = "location selected",
                        snippet = "${locationSelected.latitude},${locationSelected.longitude}",
                    )
                }
            }
            //buttons
            Column(modifier = Modifier
                .align(Alignment.BottomEnd)){
                FloatingActionButton(
                    modifier = Modifier.padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    onClick = {
                        when {
                            (!permissionState.status.isGranted) -> {
                                permissionState.launchPermissionRequest()
                            }
                            (!LocationClient.checkLocationEnabled(context)) -> {
                                context.toast("turn on the location")
                            }
                            else -> {
                                context.toast("getting location")
                                locationClient.getCurrentLocationUpdates(
                                    context = context,
                                    oneShot = true
                                ) {
                                    viewModel.locationSelected.value =
                                        LatLng(it.latitude, it.longitude)
                                }
                            }
                        }
                    }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_my_location),
                        contentDescription = "current location button"
                    )
                }
                //done button only shows when a location is selected
                if(locationSelected.validLatLng()) {
                    FloatingActionButton(
                        modifier = Modifier.padding(8.dp),
                        containerColor = MaterialTheme.colorScheme.primary,
                        onClick = {
                            //set result
                            navController.previousBackStackEntry?.savedStateHandle?.also {
                                it[LAT_KEY] = locationSelected.latitude
                                it[LNG_KEY] = locationSelected.longitude
                            }
                            navController.navigateUp()
                        }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            tint = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primary),
                            contentDescription = "current location button"
                        )
                    }
                }


            }

        }
    }
}


//used to get co ordinates of provided location name
//returns null if location not found or any network issues
fun geoLocate(geoCoder:Geocoder,locationName: String): LatLng? {
    var result:LatLng? = null
    try {
        //de
        geoCoder.getFromLocationName(locationName,1)?.firstOrNull()?.let {
                result =  LatLng(it.latitude,it.longitude)
        }
    }
    catch (_:IOException){
        //ERR STATE
    }
    return result
}
