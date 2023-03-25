package com.example.kanjimemory.sharedComposables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.ui.theme.Purple200


@ExperimentalMaterialApi
@Composable
fun MemoryCard() {

    Column {
        var checked by remember { mutableStateOf(false) }

        IconToggleButton(
            checked = checked,
            onCheckedChange = { checked = it },
            modifier = Modifier
                .width(200.dp)
                .height(130.dp)
                .padding(20.dp)
        ) {
            val tint by animateColorAsState(if (checked) Color(0xFFE2D4F1) else Purple200)

            Card(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, color = Color.White),
                backgroundColor = tint,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Kanji",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(30.dp))

        Card(modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .padding(20.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, color = Color.White),
            backgroundColor = Purple200,
            onClick = {
            }) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Kanji", style = MaterialTheme.typography.h4)
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun CardPreview() {
    MemoryCard()
}