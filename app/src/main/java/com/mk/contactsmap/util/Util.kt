package com.mk.contactsmap

import android.content.Context
import android.location.Location
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.compose.ui.text.toUpperCase
import com.google.android.gms.maps.model.LatLng
import com.mk.contactsmap.model.room.Contact
import com.mk.peoplewebclient.Profile
import java.util.*

sealed class ContextActions(){
    class MakeToast(val text:String): ContextActions()
}

infix fun ContextActions.using(context:Context){
    when(this){
        is ContextActions.MakeToast -> {
            context.toast((text))
        }
        else -> {}
    }
}

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


fun List<Profile>.toContacts():List<Contact>{
    return map { it.toContact() }
}
fun Profile.toContact():Contact{
    return Contact(
        photoPath=picture.large,
        name = name.first,
        number = phone,
        email = email,
        gender = gender.uppercase(Locale.ROOT),
        location = com.mk.contactsmap.model.room.Location(
            "_",
            location.coordinates.latitude.toDouble(),
            location.coordinates.latitude.toDouble()
        )
    )
}