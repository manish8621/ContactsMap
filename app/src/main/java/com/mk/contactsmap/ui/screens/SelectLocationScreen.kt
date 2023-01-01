package com.mk.contactsmap.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.model.LAT_KEY
import com.mk.contactsmap.model.LNG_KEY
import com.mk.contactsmap.ui.viewModel.MainViewModel

@Composable
fun SelectLocationScreen(navController: NavHostController, viewModel: MainViewModel){
    Column(modifier = Modifier.fillMaxSize()) {

        var locationSelected by rememberSaveable() {
            mutableStateOf(LatLng(0.0,0.0))
        }
        val cameraPosition = rememberCameraPositionState(){
            CameraPosition.fromLatLngZoom(locationSelected,12F)
        }

        CustomAppBar(title = "select a location"
            , onCancelClicked = {
                navController.navigateUp()
            },
            onDoneClicked = if(locationSelected.latitude != 0.0 && locationSelected.longitude != 0.0)
            {
                {
                    //set result
                    navController.previousBackStackEntry?.savedStateHandle?.also {
                        it[LAT_KEY] = locationSelected.latitude
                        it[LNG_KEY] = locationSelected.longitude
                    }
                    navController.navigateUp()
                }
            }else null
        )
        Box(modifier = Modifier.fillMaxSize()){
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = MapUiSettings(zoomControlsEnabled = false),
                onMapClick = {
                    locationSelected = it
                }
            ) {
                //mark only if user selected a location
                if (locationSelected.latitude != 0.0 && locationSelected.longitude != 0.0) {
                    Marker(
                        state = MarkerState(locationSelected),
                        title = "location selected",
                        snippet = "${locationSelected.latitude},${locationSelected.longitude}",
                    )
                }
            }
        }
    }
}