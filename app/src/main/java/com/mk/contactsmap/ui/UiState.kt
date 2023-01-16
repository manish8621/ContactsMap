package com.mk.contactsmap.ui

import com.mk.contactsmap.model.room.Contact

sealed class UiState {
    class Error(val msg:String):UiState()
    object Loading:UiState()
    object Refreshing:UiState()
    object Idle:UiState()
}