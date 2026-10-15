package io.github.Kodxi.carspotterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme

@Composable
fun CarSpotterSearch(){

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    CarSpotterAppTheme {
        CarSpotterSearch()
    }
}