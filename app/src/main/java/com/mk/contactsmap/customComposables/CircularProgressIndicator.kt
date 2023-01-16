package com.mk.contactsmap.customComposables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mk.contactsmap.ui.viewModel.DashboardViewModel
import org.intellij.lang.annotations.JdkConstants.FontStyle

@Composable
fun CircularIndicator(
    canvasSize: Dp = 300.dp,
    indicatorValue:Int=0,
    maxIndicatorValue:Int=100,
    backgroundIndicatorColor :Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
    backgroundIndicatorStrokeWidth: Dp = 100.dp,
    foregroundIndicatorColor :Color = MaterialTheme.colorScheme.primary,
    foregroundIndicatorStrokeWidth: Dp = 70.dp,
    smallText:String,
    smallTextStyle:TextStyle = MaterialTheme.typography.labelSmall,
    smallTextColor: Color = MaterialTheme.colorScheme.onBackground,
    bigText:String,
    bigTextSuffix:String,
    bigTextStyle: TextStyle = MaterialTheme.typography.headlineLarge,
    bigTextColor: Color = MaterialTheme.colorScheme.onBackground
) {

    var animatedIndicatorValue by remember{ mutableStateOf(0f) }

    //to not to go over the max value or go under 0
    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if(indicatorValue > maxIndicatorValue) {
        maxIndicatorValue
    }
    else {
        if (indicatorValue < 0) {
            0 //min value
        }
        else {
            indicatorValue
        }
    }


    //to animate value change
    LaunchedEffect(key1 = indicatorValue){
        animatedIndicatorValue = (allowedIndicatorValue.toFloat())
    }

    val percentage = (animatedIndicatorValue/maxIndicatorValue)*100

    val sweepAngle by animateFloatAsState(
        //here 2.4 is based on the 240f max_angle of arc
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )

    Column(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            //size will be reduced here
            val componentSize = size / 1.25f

            circularBackgroundIndicator(
                componentSize = componentSize,
                indicatorColor = backgroundIndicatorColor,
                indicatorStrokeWidth = backgroundIndicatorStrokeWidth
            )
            circularForegroundIndicator(
                componentSize = componentSize,
                indicatorColor = foregroundIndicatorColor,
                indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
                sweepAngle = sweepAngle
            )
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        EmbeddedElements(
            smallText = smallText,
            smallTextStyle = smallTextStyle,
            smallTextColor = smallTextColor,
            bigText = bigText,
            bigTextSuffix = bigTextSuffix,

            bigTextStyle = bigTextStyle,
            bigTextColor = bigTextColor
        )
    }
    

}

@Composable
fun EmbeddedElements(smallText:String,
                     smallTextStyle:TextStyle,
                     smallTextColor: Color,
                     bigText:String,
                     bigTextSuffix:String,
                     bigTextStyle: TextStyle,
                     bigTextColor: Color
) {
    Text(text = smallText, style = smallTextStyle, color = smallTextColor)
    Text(text = "$bigText $bigTextSuffix", style = bigTextStyle, color = bigTextColor)
}

fun DrawScope.circularBackgroundIndicator(componentSize: Size,
                                          indicatorColor:Color,
                                          indicatorStrokeWidth:Dp) {
    drawArc(color = indicatorColor,
        size = componentSize,
        startAngle = 150f,
        sweepAngle = 240f,
        //connects start and end with center point of arc
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth.value,
            cap = StrokeCap.Round,

        ),
        topLeft = Offset(
            x=(size.width - componentSize.width)/2,
            y= (size.height - componentSize.height)/2
        )
    )
}

fun DrawScope.circularForegroundIndicator(componentSize: Size,
                                          indicatorColor:Color,
                                          indicatorStrokeWidth:Dp,
                                          sweepAngle:Float) {
    drawArc(color = indicatorColor,
        size = componentSize,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        //connects start and end with center point of arc
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth.value,
            cap = StrokeCap.Round,

        ),
        topLeft = Offset(
            x=(size.width - componentSize.width)/2,
            y= (size.height - componentSize.height)/2
        )
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CircularIndicatorPreview() {

    var indicatorValue by remember {
        mutableStateOf(100)
    }
    val context = LocalContext.current

    Scaffold(topBar = { CustomAppBar(navController = rememberNavController()
        , title =  "Dashboard") }) {

        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize()
            .clickable {
                indicatorValue -= 10
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            CircularIndicator(indicatorValue = indicatorValue,
                bigText = indicatorValue.toString(),
                bigTextSuffix = "GB",
                smallText = "Remaining"
            )

            BorderLessCurvedTextField(value = indicatorValue.toString(),
                onValChange = {
                    if(it.isNotEmpty())indicatorValue = it.toInt()
                },
                placeholder = "value",
                keyboardType = KeyboardType.Number
            )
        }
    }
}
