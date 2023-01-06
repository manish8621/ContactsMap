@file:OptIn(ExperimentalMaterial3Api::class)

package com.mk.contactsmap.customComposables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mk.contactsmap.*
import com.mk.contactsmap.R
import com.mk.contactsmap.model.COUNTRY_CODES

@Composable
fun PlaceHolder(text :String) {
    Text(text = text, style = MaterialTheme.typography.labelMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BorderLessCurvedTextField(
    value: String,
    onValChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
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
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        enabled = enabled,
        singleLine = true,
        readOnly = readOnly,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun BoxScope.SearchBox(
    value: String,
    onValueChange:(String)->Unit,
    placeholder: String,
    isLoading:Boolean,
    onClearClicked:()->Unit,
    onSearch:KeyboardActionScope.()->Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape= RoundedCornerShape(60),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .zIndex(10F)
        ,
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            containerColor = MaterialTheme.colorScheme.background,
            disabledIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            if(isLoading){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(25.dp)
                        .align(Alignment.Center)
                )
            }
            else {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search location button"
                )
            }
        },
        trailingIcon = if(value.isNotEmpty()){
            {
                IconButton(onClick = onClearClicked) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "search field clear button"
                    )
                }
            }
        }
        else {
            null
        },
        enabled = isLoading.not()
        ,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = onSearch)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowScope.BorderLessCurvedTextField(
    value: String,
    onValChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: @Composable() (() -> Unit)? = null,
    trailingIcon: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValChange,
        shape= RoundedCornerShape(40),
        modifier = Modifier
            .weight(1F)
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
        trailingIcon = trailingIcon,
        enabled = enabled,
        singleLine = true,
        readOnly = readOnly,
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
                onClick = {
                    navController.navigate(navItem.route){

                        popUpTo(navController.graph.startDestinationId){
                            saveState=true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = navItem.icon, contentDescription = navItem.label)},
                label = {Text(text = navItem.label, style = MaterialTheme.typography.labelSmall)},
                alwaysShowLabel = false
                )
        }
    }
}

@Composable
fun CountryCodeSelector(
    codes: List<String> = COUNTRY_CODES,
    onSelected: (label: String) -> Unit,
    value: String
) {
    Column(modifier = Modifier.padding(8.dp)){
        var expanded by remember {
            mutableStateOf(false)
        }

        TextField(value = value,
            onValueChange = {},
            modifier = Modifier
                .width(100.dp),
            readOnly = true,
            shape = RoundedCornerShape(40),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "country code drop down"
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(
                100.dp
            ).height(200.dp)
        ) {

            codes.forEach { code ->
                DropdownMenuItem(
                    text = { Text(code) },
                    onClick = {
                        onSelected(code)
                        expanded = false
                    })
            }

        }
    }
}


@Preview
@Composable
fun CustomPreview() {
//    Scaffold(topBar = { CustomAppBar("Contacts",rememberNavController(),{}) }, content = {
//       Text("hello rold",Modifier.padding(it))
//    })

}
