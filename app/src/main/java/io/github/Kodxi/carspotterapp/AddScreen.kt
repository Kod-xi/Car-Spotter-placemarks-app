@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class, ExperimentalTime::class)

package io.github.Kodxi.carspotterapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.Kodxi.carspotterapp.details.CarDetails
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid



@Composable
fun CarSpotterAdd(onSave: (CarDetails) -> Unit, onReturn: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Add Car",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC78EFF))
                .padding(12.dp),

            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        val brandModelState = rememberTextFieldState(initialText = "")
        val descriptionState = rememberTextFieldState(initialText = "")
        val datePickerState = rememberDatePickerState(
            initialSelectedDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).toJavaLocalDate(),
            initialDisplayMode = DisplayMode.Input
        )
        val xCoordState = rememberTextFieldState(initialText = "")
        val yCoordState = rememberTextFieldState(initialText = "")

        Card(
            Modifier.weight(1f)
                .padding(5.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
            elevation = CardDefaults.cardElevation(2.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Brand / Model",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    state = brandModelState,
                    modifier = Modifier.padding(bottom = 5.dp),
                    lineLimits = TextFieldLineLimits.SingleLine,
                    textStyle =
                        TextStyle(
                            fontSize = 22.sp,
                        ),
                    shape = RoundedCornerShape(48.dp)
                )

                Text(
                    text = "Description",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                TextField(
                    state = descriptionState,
                    modifier = Modifier.padding(bottom = 5.dp),
                    lineLimits = TextFieldLineLimits.Default,
                    textStyle =
                        TextStyle(
                            fontSize = 22.sp,
                        ),
                    shape = RoundedCornerShape(48.dp)
                )

                Text(
                    text = "Date Spotted",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                DatePicker(
                    state = datePickerState,
                    showModeToggle = true,
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(28.dp)),
                )

                Text(
                    text = "Location",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "X:",
                        modifier = Modifier.padding(bottom = 5.dp, end = 5.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextField(
                        state = xCoordState,
                        modifier = Modifier.padding(bottom = 5.dp, end = 5.dp)
                            .weight(2f),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle =
                            TextStyle(
                                fontSize = 22.sp,
                            ),
                        shape = RoundedCornerShape(48.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )

                    Text(
                        text = "Y:",
                        modifier = Modifier.padding(bottom = 5.dp, end = 5.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    TextField(
                        state = yCoordState,
                        modifier = Modifier.padding(bottom = 5.dp, end = 5.dp)
                            .weight(2f),
                        lineLimits = TextFieldLineLimits.SingleLine,
                        textStyle =
                            TextStyle(
                                fontSize = 22.sp,
                            ),
                        shape = RoundedCornerShape(48.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }

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
                onClick = {
                    val newCar = CarDetails(
                        brandModel = brandModelState.text.toString(),
                        description = descriptionState.text.toString(),
                        dateSpotted = datePickerState.getSelectedDate()?.toKotlinLocalDate(),
                        x = xCoordState.text.toString().toDoubleOrNull() ?: 0.0,
                        y = yCoordState.text.toString().toDoubleOrNull() ?: 0.0
                    )
                    onSave(newCar)
                },
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Check",
                    tint = Color.Black
                )
            }

            IconButton(
                onClick = {
                    onReturn()
                },
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Clear",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    CarSpotterAppTheme {
        CarSpotterAdd(
            onSave = {},
            onReturn = {}
        )
    }
}