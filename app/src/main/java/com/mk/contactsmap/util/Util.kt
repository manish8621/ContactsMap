package com.mk.contactsmap

import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.mk.contactsmap.model.room.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun String?.isValid() = (this!=null && (isNotBlank() && isNotEmpty()))

fun isValid(vararg data:String) = (data.count{ it.isValid().not() } == 0)

fun isValid(contact: Contact) = (contact.name.isValid() && contact.number.isValid() && contact.email.isValid())

fun LatLng.validLatLng()= (latitude != 0.0 && longitude != 0.0)

fun Context.toast(text:String){
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Location.toLatLng() = LatLng(latitude,longitude)

fun Context.checkInternet():Boolean{
    return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork!=null
}
