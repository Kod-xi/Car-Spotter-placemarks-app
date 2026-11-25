package io.github.Kodxi.carspotterapp

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.views.overlay.MapEventsOverlay
import kotlin.uuid.Uuid

@Composable
fun MapPicker(startLat: Double, startLon: Double, onConfirm: (Double, Double) -> Unit, onCancel: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val mapView = remember {
        Configuration.getInstance().userAgentValue = context.packageName
        MapView(context)
    }
    val marker = remember { Marker(mapView) }
    var selected by remember { mutableStateOf(GeoPoint(startLat, startLon)) }

    LaunchedEffect(startLat, startLon) {
        val p = GeoPoint(startLat, startLon)
        selected = p
        marker.position = p
        mapView.controller.setCenter(p)
        mapView.invalidate()
    }

    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDetach()
        }
    }

    Column(Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                mapView.apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    controller.setZoom(18.0)
                    controller.setCenter(selected)

                    marker.position = selected
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    overlays.add(marker)

                    val receiver = object : MapEventsReceiver {
                        override fun singleTapConfirmedHelper(point: GeoPoint): Boolean {
                            selected = point
                            marker.position = point
                            invalidate()
                            return true
                        }

                        override fun longPressHelper(point: GeoPoint): Boolean {
                            selected = point
                            marker.position = point
                            invalidate()
                            return true
                        }
                    }
                    overlays.add(MapEventsOverlay(receiver))
                }
            },
            modifier = Modifier.weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFC78EFF)),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    onConfirm(selected.latitude, selected.longitude)
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
                onClick = {onCancel()},
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
fun MapPickerPreview() {
    CarSpotterAppTheme {
        MapPicker(0.0, 0.0, {_,_ ->}, {} )
    }
}