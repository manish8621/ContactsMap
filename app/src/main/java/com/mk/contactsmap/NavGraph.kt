package com.mk.contactsmap

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mk.contactsmap.screens.*
import com.mk.contactsmap.ui.screens.*
import com.mk.contactsmap.ui.viewModel.MainViewModel

val bottomNavItems =listOf(
    BottomNavItem("Add", Icons.Default.Add, Screen.AddContactScreen.route) ,
    BottomNavItem("Contacts list", Icons.Default.Person, Screen.ShowContactsScreen.route),
    BottomNavItem("Map", Icons.Default.LocationOn, Screen.ShowContactsOnMapScreen.route)
)

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ShowContactsScreen.route,
        modifier = Modifier.padding(paddingValues = paddingValues)
    ){
        composable(route = Screen.AddContactScreen.route) { AddContactScreen(navController,viewModel) }
        composable(route = Screen.ShowContactsScreen.route) { ShowContactsScreen(navController,viewModel) }
        composable(route = Screen.SelectLocationScreen.route) { SelectLocationScreen(navController,viewModel) }
        composable(route = Screen.ShowContactsOnMapScreen.route) { ShowContactsOnMapScreen(navController,viewModel) }
    }
}
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route:String,
)