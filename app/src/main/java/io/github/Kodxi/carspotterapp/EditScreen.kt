@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3Api::class, ExperimentalTime::class)

package io.github.Kodxi.carspotterapp


import android.Manifest
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.DisplayMode
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import timber.log.Timber

@Composable
fun CarSpotterUpdate(
    car: CarDetails?,
    onReturn: () -> Unit,
    hasLocationPermission: Boolean,
    onRequestLocation: () -> Unit,
) {

    val brandModelState = rememberTextFieldState(initialText = car?.brandModel ?: "")
    val descriptionState = rememberTextFieldState(initialText = car?.description ?: "")
    val datePickerState = rememberDatePickerState(
        initialSelectedDate = Clock.System.todayIn(TimeZone.currentSystemDefault()).toJavaLocalDate(),
        initialDisplayMode = DisplayMode.Input
    )
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(car?.imageUri?.let { Uri.parse(it)}) }
    val context = LocalContext.current
    val contentResolver = context.contentResolver
    val picker = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        if (uri != null) {
            // Keep access to the document URI for later use, when supported.
            try {
                contentResolver.takePersistableUriPermission(
                    uri,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            } catch (_: SecurityException) {
                // Some providers may not grant persistable access.
            }

            imageUri = uri
        }
    }

    var showMap by remember {
        mutableStateOf(false)
    }

    val xCoordState = rememberTextFieldState(initialText = car?.x.toString())
    val yCoordState = rememberTextFieldState(initialText = car?.y.toString())

    if(!showMap){
        Column(
            modifier = Modifier.fillMaxSize(),
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
                color = Color.Black
            )




            Card(
                Modifier.weight(1f)
                    .padding(5.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(2.dp)
            ){
                Column(
                    modifier = Modifier.padding(10.dp)
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
                        modifier = Modifier.padding(bottom = 5.dp)
                            .padding(bottom = 5.dp)
                            .clip(RoundedCornerShape(28.dp)),
                    )

                    Text(
                        text = "Photo of Car",
                        modifier = Modifier.padding(bottom = 5.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    if(imageUri != null){
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Image of the spotted car.",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                                .height(180.dp)
                                .clickable{
                                    picker.launch(arrayOf("image/*"))
                                }
                        )
                    }
                    else{
                        Image(
                            painterResource(android.R.drawable.ic_menu_gallery),
                            contentDescription = "No image provided yet.",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxWidth()
                                .height(180.dp)
                                .clickable{
                                    picker.launch(arrayOf("image/*"))
                                }
                        )
                    }


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

                        IconButton(
                            onClick = {
                                if(!hasLocationPermission){
                                    onRequestLocation()
                                }
                                showMap = true
                            }
                        ){
                            Icon(
                                Icons.Filled.Map,
                                contentDescription = "Check",
                            )

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
                    onClick = {
                        val updatedCar = CarDetails(
                            id = car?.id ?: Uuid.random(),
                            brandModel = brandModelState.text.toString(),
                            description = descriptionState.text.toString(),
                            dateSpotted = datePickerState.getSelectedDate()?.toKotlinLocalDate(),
                            imageUri = imageUri?.toString(),
                            x = xCoordState.text.toString().toDoubleOrNull() ?: 0.0,
                            y = yCoordState.text.toString().toDoubleOrNull() ?: 0.0
                        )
                        AppData.carStore.update(updatedCar)
                        onReturn()
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
                    onClick = {onReturn()},
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

    else{
        var deviceLocation by remember { mutableStateOf<Pair<Double, Double>?>(null) }

        LaunchedEffect(hasLocationPermission){
            if(hasLocationPermission){
                try{
                    val client = LocationServices.getFusedLocationProviderClient(context)
                    client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                        .addOnSuccessListener { location ->
                            if(location != null){
                                deviceLocation = Pair(location.latitude, location.longitude)
                            }
                            else{
                                Timber.d("Location is null")
                            }
                        }
                } catch (e: SecurityException){
                    Timber.d("Location Permission Denied")
                }

            }
        }

        val startLat = xCoordState.text.toString().toDoubleOrNull() ?: deviceLocation?.first ?: 53.35
        val startLon = yCoordState.text.toString().toDoubleOrNull() ?: deviceLocation?.second ?: -6.25

        MapPicker(
            startLat = startLat,
            startLon = startLon,
            onConfirm = { lat, lon ->
                xCoordState.setTextAndPlaceCursorAtEnd(lat.toString())
                yCoordState.setTextAndPlaceCursorAtEnd(lon.toString())
                showMap = false
            },
            onCancel = { showMap = false }
        )
    }


}

@Preview(showBackground = true)
@Composable
fun CarSpotterPreview() {
    CarSpotterAppTheme {
        CarSpotterUpdate(CarDetails(), onReturn = {}, false,) {}
    }
}