package com.mk.contactsmap.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.mk.contactsmap.customComposables.BorderLessCurvedTextField
import com.mk.contactsmap.customComposables.CircularIndicator
import com.mk.contactsmap.customComposables.CustomAppBar
import com.mk.contactsmap.toast
import com.mk.contactsmap.ui.viewModel.DashboardViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavHostController,
                    viewModel: DashboardViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val totalCount by viewModel.totalCount.observeAsState(initial = 1)
    val maleCount by viewModel.maleCount.observeAsState(initial = 0)
    val femaleCount by viewModel.femaleCount.observeAsState(initial = 0)


    Scaffold(topBar = { CustomAppBar(navController = navController
        , title =  "Dashboard") }) {

        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally)
        {

            //male female count
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(10)
                )
                .padding(start = 20.dp, end = 20.dp, top = 30.dp, bottom = 10.dp)
                , horizontalArrangement = Arrangement.SpaceBetween
            ){

                if(totalCount > 0 ){

                    CircularIndicator(
                        canvasSize = 150.dp,
                        indicatorValue = maleCount,
                        maxIndicatorValue = totalCount,
                        bigText = maleCount.toString(),
                        bigTextSuffix = "",
                        smallText = "Male"
                    )

                    CircularIndicator(
                        canvasSize = 150.dp,
                        indicatorValue = femaleCount,
                        maxIndicatorValue = totalCount,
                        bigText = femaleCount.toString(),
                        bigTextSuffix = "",
                        smallText = "Female"
                    )
                }
            }

            //space
            Spacer(modifier = Modifier.height(100.dp))

            //total count
            Text(
                text = "Total",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = totalCount.toString(),
                style = MaterialTheme.typography.displayLarge
            )
        }

    }
}