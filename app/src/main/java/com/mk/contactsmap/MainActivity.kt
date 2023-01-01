package com.mk.contactsmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mk.contactsmap.customComposables.CustomBottomNavigationBar
import com.mk.contactsmap.ui.screens.Screen
import com.mk.contactsmap.ui.theme.ContactsMapTheme
import com.mk.contactsmap.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsMapTheme {
                //TODO:Remove this
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    navController = rememberNavController()
                    //sets the content and other widgets
                    Scaffold(
                        bottomBar = { CustomBottomNavigationBar(navController = navController) }
                    ) { paddingValues ->
                        SetupNavGraph(navController = navController,paddingValues,viewModel)
                    }
                }
            }
        }
    }

}

