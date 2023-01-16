package com.mk.contactsmap.customComposables

import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.typography

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorMessageText(msg: String) {
    Text(msg,
        style = typography.labelSmall.copy(
            color = MaterialTheme.colorScheme.onError
        )
    )
}