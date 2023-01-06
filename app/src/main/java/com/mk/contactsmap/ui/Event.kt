package com.mk.contactsmap.ui

import com.google.android.gms.maps.model.LatLng
import com.mk.contactsmap.model.room.Contact
import com.mk.contactsmap.model.room.Location

sealed class Event {
    class ContactCreate(val location: Location?):Event()
    class ContactDelete(val contact: Contact):Event()
    class LocationSelect(val latLng: LatLng):Event()
    object LoadingStarted:Event()
    object LoadingEnded:Event()
}