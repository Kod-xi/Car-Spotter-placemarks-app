@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class, ExperimentalTime::class)

package io.github.Kodxi.carspotterapp

import android.os.Build
import android.os.Bundle
import android.widget.NumberPicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.Kodxi.carspotterapp.details.CarDetails
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.todayIn
import org.w3c.dom.Text
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Composable
fun CarSpotterUpdate(car: CarDetails?, onReturn: () -> Unit ) {
    Column(
        modifier = Modifier.fillMaxSize()
            .scrollable(
                ScrollableState { 1f},
                orientation = Orientation.Vertical
            ),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Edit Car",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC78EFF))
                .padding(12.dp),

            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
        )

        val brandModelState = rememberTextFieldState(initialText = car?.brandModel ?: "")
        val descriptionState = rememberTextFieldState(initialText = car?.description ?: "")
        val datePickerState = rememberDatePickerState(
            initialSelectedDate = car?.dateSpotted?.toJavaLocalDate() ?:
                Clock.System.todayIn(TimeZone.currentSystemDefault()).toJavaLocalDate(),
        )

        Box(
            modifier = Modifier.padding(3.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .shadow(2.dp)
                .weight(1f)
        ){
            Column(
                modifier = Modifier.padding(10.dp)
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
                    modifier = Modifier.padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(28.dp)),
                )

                Text(
                    text = "${datePickerState.getSelectedDate()}",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(){

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
                    val updatedCar = CarDetails(car?.id ?: Uuid.random(), brandModelState.text.toString(), descriptionState.text.toString(), datePickerState.getSelectedDate()?.toKotlinLocalDate() )
                    AppData.carStore.update(updatedCar)
                    onReturn()
                },
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Check"
                )
            }

            IconButton(
                onClick = {onReturn()},
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Clear,
                    contentDescription = "Clear"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarSpotterPreview() {
    CarSpotterAppTheme {
        CarSpotterUpdate(CarDetails(), onReturn = {})
    }
}