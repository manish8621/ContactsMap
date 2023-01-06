package com.mk.contactsmap

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mk.contactsmap.customComposables.CustomBottomNavigationBar
import com.mk.contactsmap.model.COUNTRY_CODES
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
                    //sets the content and other widgets
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = { CustomBottomNavigationBar(navController = navController) }
                    ) { paddingValues ->
                        SetupNavGraph(navController = navController,paddingValues)
                    }
            }
        }
    }


}

