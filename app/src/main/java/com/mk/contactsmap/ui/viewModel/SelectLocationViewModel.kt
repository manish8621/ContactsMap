package com.mk.contactsmap.ui.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.mk.contactsmap.ui.Event
import com.mk.contactsmap.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow


class SelectLocationViewModel(): ViewModel() {
    val locationSearchQuery = MutableStateFlow("")
    val locationSelected = MutableStateFlow(LatLng(0.0,0.0))

    val uiState = MutableStateFlow<UiState>(UiState.Idle)

    fun setLocationSearchQuery(query:String){
        locationSearchQuery.value = query
    }
    fun clearLocationSearchQuery(){
        locationSearchQuery.value = ""
    }
    fun selectLocation(location:LatLng){
        locationSelected.value = location
    }
    fun handleEvent(event: Event){
        when(event){
            is Event.LoadingStarted -> uiState.value = UiState.Loading
            is Event.LoadingEnded -> uiState.value = UiState.Idle
            is Event.LocationSelect -> locationSelected.value = event.latLng
            else -> {uiState.value = UiState.Idle}
        }
    }
}