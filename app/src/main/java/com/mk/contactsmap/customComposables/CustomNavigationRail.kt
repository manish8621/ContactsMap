package com.mk.contactsmap.customComposables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mk.contactsmap.navigation.NavItem
import com.mk.contactsmap.navigation.homeNavItems

@Composable
fun CustomNavigationRail(navController: NavHostController,
                         items:List<NavItem> = homeNavItems
) {
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route
    NavigationRail(
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach{ navItem->
            NavigationRailItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route){
                        popUpTo(navController.graph.startDestinationId){
                            saveState = true
                        }
                        restoreState=true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = "navigation icon button")
                },
                label = {Text(navItem.label)},
                alwaysShowLabel = false
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}