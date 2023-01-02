@file:OptIn(ExperimentalMaterial3Api::class)

package com.mk.contactsmap.customComposables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mk.contactsmap.R
import com.mk.contactsmap.bottomNavItems

@Composable
fun PlaceHolder(text :String) {
    Text(text = "name", style = MaterialTheme.typography.labelMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderLessCurvedTextField(
    value: String,
    onValChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValChange,
        shape= RoundedCornerShape(40),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            }
        ,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = leadingIcon,
        enabled = enabled,
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(title:String= stringResource(id = R.string.app_name),
                 navController: NavHostController, onDoneClicked: (() -> Unit)? = null){
        TopAppBar(
            title = {Text(text= title)}
            ,colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary
            , titleContentColor = MaterialTheme.colorScheme.onPrimary
            , actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            , navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
            ,navigationIcon = {
                        if(navController.previousBackStackEntry!=null){
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                        }
                        }
                }
            ,actions={
                onDoneClicked?.let{
                    IconButton(onClick = onDoneClicked) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = "done btn")
                    }
                }
            }
        )
}

@Composable
fun CustomBottomNavigationBar(navController:NavHostController) {

    NavigationBar() {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        //get current screen route
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEach{
                navItem->
            NavigationBarItem(
                //check if current route and the item's route matches
                selected = currentRoute==navItem.route,
                onClick = { navController.navigate(navItem.route) },
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label)},
                label = {Text(text = navItem.label, style = MaterialTheme.typography.labelSmall)},
                alwaysShowLabel = false
                )
        }
    }
}
@Preview
@Composable
fun CustomPreview() {
    Scaffold(topBar = { CustomAppBar("Contacts",rememberNavController(),{}) }, content = {
       Text("hello rold",Modifier.padding(it))
    })
}
