@file:OptIn(ExperimentalUuidApi::class)

package io.github.Kodxi.carspotterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun CarSpotterSearch(onReturn: () -> Unit){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = "Find Car",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC78EFF))
                .padding(12.dp),

            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        val uuidState = rememberTextFieldState(initialText = "")
        val isUuidValid by remember {
            derivedStateOf { uuidState.text.toString().trim().length == 36 }
        }

        Card(
            Modifier.fillMaxWidth()
                .padding(5.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(2.dp)
        ){
            Column(
                Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Enter car Id",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )

                TextField(
                    state = uuidState,
                    modifier = Modifier.padding(bottom = 5.dp),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle =
                        TextStyle(
                            fontSize = 22.sp,
                        ),
                    shape = RoundedCornerShape(48.dp)
                )
            }
        }

        if(isUuidValid){
            val car = AppData.carStore.findOne(Uuid.parse(uuidState.text.toString().trim()))
            if( car != null){
                Card(
                    Modifier.fillMaxWidth()
                        .padding(5.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                    elevation = CardDefaults.cardElevation(2.dp)
                ){
                    Column(Modifier.padding(10.dp)) {
                        Text(
                            text = "Brand / Model",
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = car.brandModel,
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp
                        )

                        Text(
                            text = "Description",
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = car.description,
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp
                        )

                        Text(
                            text = "Date Spotted",
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = car.dateSpotted.toString(),
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp
                        )

                        Text(
                            text = "Coordinates",
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "( ${car.x} , ${car.y} )",
                            modifier = Modifier.padding(bottom = 5.dp),
                            fontSize = 24.sp
                        )
                    }
                }
            }
            else{
                Card(
                    Modifier.weight(1f)
                ){

                }
            }
        }
        else{
            Card(
                Modifier.weight(1f)
            ){

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFC78EFF)),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {onReturn()},
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    CarSpotterAppTheme {
        CarSpotterSearch(onReturn = {})
    }
}