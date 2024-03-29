package com.example.kanjimemory.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.sharedComposables.DragDropLogicScreen
import com.example.kanjimemory.sharedComposables.DragTarget
import com.example.kanjimemory.sharedComposables.DropItem
import com.example.kanjimemory.sharedComposables.TopBar
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.ui.theme.Purple500
import com.example.kanjimemory.viewmodel.DragDropViewModel

@Composable
fun DragDropScreen(
    navController: NavController = rememberNavController(),
    dragDropViewModel: DragDropViewModel,
    firstTranslationItem: Kanji?,
    dragDropKanjis: List<Kanji>
) {
    Scaffold(topBar = {
        TopBar(navController = navController)
    }) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), color = MaterialTheme.colors.primary
        ) {

            DragDropLogicScreen(
                modifier = Modifier.fillMaxSize()
            ) {

                val screenWidth = LocalConfiguration.current.screenWidthDp
                val screenHeight = LocalConfiguration.current.screenHeightDp

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height((screenHeight / 6).dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        dragDropKanjis.forEach { kanji ->
                            DragTarget(
                                dataToDrop = kanji, dragDropViewModel = dragDropViewModel
                            ) {
                                DraggableKanjiCard(item = kanji, screenWidth = screenWidth)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height((screenHeight / 10).dp))

                    val valueContext = LocalContext.current

                    DropItem<Kanji> { isInBound, kanjiItem ->
                        if (kanjiItem != null) {
                            // only if user drops item, then kanjiItem (the data) will not be null
                            dragDropViewModel.checkIfMatch(kanjiItem)
                            Toast.makeText(
                                valueContext,
                                dragDropViewModel.displayToast.value,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        FixedTranslation(
                            item = firstTranslationItem, isInBound = isInBound
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FixedTranslation(item: Kanji?, isInBound: Boolean) {
    Card(
        elevation = 8.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, color = Color.White),
        backgroundColor = if (isInBound) Purple200 else Purple500,
        modifier = Modifier
            .width(300.dp)
            .height(170.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item?.translation?.let {
                Text(
                    text = it, style = MaterialTheme.typography.h3, textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun DraggableKanjiCard(item: Kanji, screenWidth: Int) {
    Box(
        modifier = Modifier
            .size((screenWidth / 6f).dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Purple200)
            .border(BorderStroke(2.dp, color = Color.White), shape = RoundedCornerShape(20.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = item.kanji,
            style = MaterialTheme.typography.h4,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FixedCardPreview() {
    FixedTranslation(
        item = Kanji(
            translation = "person", kanji = "人", id = 0, dateTranslated = 123
        ), isInBound = false
    )
}

@Preview(showBackground = true)
@Composable
fun KanjiCardPreview() {
    DraggableKanjiCard(
        item = Kanji(
            translation = "person", kanji = "人", id = 0, dateTranslated = 123
        ), screenWidth = LocalConfiguration.current.screenWidthDp
    )
}