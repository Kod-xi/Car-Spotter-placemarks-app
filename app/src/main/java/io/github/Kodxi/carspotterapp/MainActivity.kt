package io.github.Kodxi.carspotterapp

import android.net.http.HeaderBlock
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp

@Composable
fun CarSpotterMain(
    onViewMarksClick: () -> Unit,
    onAddMarkClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
//
//        Button(
//            onClick = onViewMarksClick,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 24.dp)
//        ) {
//            Text("View Marks")
//        }
//
//        Button(
//            onClick = onAddMarkClick,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp)
//        ) {
//            Text("Add Mark")
//        }

        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(20) {
                Box(modifier = Modifier
                    .height(150.dp)
                    .padding(10.dp)
                    .clip(RoundedCornerShape(12.dp))
//                    .background(Color.Red)
                    .shadow(1.dp)
                    .border(
                        border = BorderStroke(Dp.Hairline, Color.Black),
                        RoundedCornerShape(12.dp)
                    ),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = "Test",
                        textAlign = TextAlign.Center
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
                    Icons.Filled.Create,
                    contentDescription = "Create"
                )
            }

            IconButton(
                onClick = {},
                Modifier.weight(1f)
            ) {
                Icon(
                    Icons.Filled.Create,
                    contentDescription = "Create"
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
            onViewMarksClick = {},
            onAddMarkClick = {}
        )
    }
}