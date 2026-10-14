@file:OptIn(ExperimentalUuidApi::class)

package io.github.Kodxi.carspotterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import android.net.http.HeaderBlock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import io.github.Kodxi.carspotterapp.details.CarDetails
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarSpotterAppTheme {

                val cars = remember { AppData.carStore.findAll().toMutableStateList() }
                CarSpotterMain(
                    cars = cars,
                    onAddCar = { car -> },
                    onEditCar = { car ->  },
                    onDeleteCar = { car -> AppData.carStore.delete(car.id) },
                    onFindCar = {car -> }
                )
            }
        }
    }
}

@Composable
fun CarSpotterMain(cars: List<CarDetails>, onAddCar: (CarDetails) -> Unit, onEditCar: (CarDetails) -> Unit, onDeleteCar: (CarDetails) -> Unit, onFindCar: (CarDetails) -> Unit ) {

    var selectedBoxId by remember { mutableStateOf<Uuid?>(null)}
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Car Spotter",
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFC78EFF))
                .padding(12.dp),

            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,

            )

        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
                .clickable {selectedBoxId = null}
        ) {

            itemsIndexed( cars ){ index, car ->
                Box(modifier = Modifier
                    .height(150.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
                    .shadow(1.dp)
                    .border(
                        border = BorderStroke(Dp.Hairline, Color.Black),
                        RoundedCornerShape(12.dp)
                    )
                    .clickable {
                        selectedBoxId = car.id
                    }
                ) {

                    Column(
                        Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = car.brandModel,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = car.dateSpotted.toString(),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)
                        )

                        if(car.id == selectedBoxId) {

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                IconButton(
                                    onClick = { onEditCar(car)},
                                    modifier = Modifier.padding(5.dp)
                                        .weight(1f)
                                ) {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = "Edit"
                                    )
                                }
                                IconButton(
                                    onClick = {onDeleteCar(car)},
                                    modifier = Modifier.padding(5.dp)
                                        .weight(1f)
                                ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Delete"
                                    )
                                }
                            }
                        }
                    }

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
                onClick = {},
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Create,
                    contentDescription = "Create"
                )
            }

            IconButton(
                onClick = {},
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search"
                )
            }

            IconButton(
                onClick = {},
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Check"
                )
            }
        }


    }


}



@Preview(showBackground = true)
@Composable
fun CarSpotterMainPreview() {
    CarSpotterAppTheme {
        CarSpotterMain(
            cars = AppData.carStore.findAll(),
            onAddCar = {},
            onEditCar = {},
            onDeleteCar = {},
            onFindCar = {}
        )
    }
}