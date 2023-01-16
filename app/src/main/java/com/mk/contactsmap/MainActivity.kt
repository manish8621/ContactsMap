package com.mk.contactsmap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mk.contactsmap.customComposables.CustomBottomNavigationBar
import com.mk.contactsmap.model.COUNTRY_CODES
import com.mk.contactsmap.navigation.RootNavGraph
import com.mk.contactsmap.ui.screens.Screen
import com.mk.contactsmap.ui.theme.ContactsMapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsMapTheme {
                navController = rememberNavController()
                RootNavGraph(navController = navController)
            }
        }
    }


}

