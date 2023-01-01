package com.mk.contactsmap.customComposables

import android.hardware.lights.Light
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mk.contactsmap.bottomNavItems

@Composable
fun PlaceHolder(text :String) {
    Text(text = "name", style = MaterialTheme.typography.labelMedium)
}

@Composable
fun BorderLessCurvedTextField(value:String,
                              onValChange:(String)->Unit,
                              placeholder: String,
                              enabled:Boolean = true,
                              keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValChange,
        shape= RoundedCornerShape(40),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = Color.Transparent
        ),
        enabled = enabled,
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}
@Composable
fun CustomAppBar(title:String,
                 onCancelClicked:(()->Unit)?=null,
                 onDoneClicked:(()->Unit)?=null) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize(animationSpec = tween(100, easing = FastOutLinearInEasing))
    )
    {
        if (onCancelClicked != null) {
            androidx.compose.material3.IconButton(onClick = onCancelClicked) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Clear,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "cancel"
                )
            }
        }
        else
        {
            Spacer(modifier = Modifier.width(50.dp))
        }
        Text(text = title, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1F), textAlign = TextAlign.Center)
        if (onDoneClicked != null) {
            androidx.compose.material3.IconButton(onClick = onDoneClicked) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Done,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = ""
                )
            }
        }
        else
        {
            Spacer(modifier = Modifier.width(50.dp))
        }
    }
}

@Composable
fun CustomBottomNavigationBar(navController:NavHostController) {
    BottomNavigation(backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer, modifier = Modifier.padding(4.dp)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        //get current screen route
        val currentRoute = navBackStackEntry?.destination?.route

        bottomNavItems.forEach{
            navItem->
                BottomNavigationItem(
                    //check if current route and the item's route matches
                    selected = currentRoute==navItem.route,
                    onClick = { navController.navigate(navItem.route) },
                    icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label)},
                    label = {Text(text = navItem.label, style = androidx.compose.material3.MaterialTheme.typography.labelSmall)},
                    alwaysShowLabel = false,
                    selectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer ,
                    unselectedContentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5F)
                )
        }
    }
}
