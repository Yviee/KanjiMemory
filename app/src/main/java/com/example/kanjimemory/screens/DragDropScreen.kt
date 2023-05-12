package com.example.kanjimemory.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.sharedComposables.DraggableScreen
import com.example.kanjimemory.sharedComposables.DropScreen
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.DragDropViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DragDropScreen(navController: NavController = rememberNavController()) {

    val dragDropViewModel: DragDropViewModel = hiltViewModel()
    val database = dragDropViewModel.randomKanjiList.collectAsState().value
    val firstTranslationItem = database.firstOrNull()
    val kanjis = database.shuffled()

    Scaffold(
        topBar = {
            TopAppBar(elevation = 3.dp, backgroundColor = Purple200) {
                Row {
                    Icon(imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()        // go back to last screen
                        })
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(text = "Back to Main Menu")
                }
            }
        })
    {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {
            DraggableScreen(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                //DropScreen(database, dragDropViewModel)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(20.dp))

                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        kanjis.forEach { kanji ->
                            DraggableKanjiCard(item = kanji)
                        }
                    }

                    Spacer(modifier = Modifier.padding(20.dp))

                    FixedTranslation(item = firstTranslationItem)

                    // using LazyGrid: https://stackoverflow.com/questions/64913067/reorder-lazycolumn-items-with-drag-drop

                    // https://blog.canopas.com/android-drag-and-drop-ui-element-in-jetpack-compose-14922073b3f1
                    // https://devblogs.microsoft.com/surface-duo/jetpack-compose-drag-and-drop/
                    // https://gist.github.com/surajsau/f5342f443352195208029e98b0ee39f3
                    // https://proandroiddev.com/basic-drag-n-drop-in-jetpack-compose-a6919ba58ba8
                    // TODO: find out why kanjis shuffle twice :S
                    // TODO: find out how to center text that goes over more than one line
                    // TODO: make Fixedtranslation dropTarget; make KanjiCard dragtarget and appear on top
                    // may have to put everything in Grid to be able to get dropCards to access fixed card
                    // implement something like: https://github.com/MatthiasKerat/DragAndDropYT/tree/main/app/src/main/java/com/kapps/draganddrop
                }
            }
        }
    }
}

@Composable
fun FixedTranslation(item: Kanji?) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, color = Color.White),
        backgroundColor = Purple200,
        modifier = Modifier
            .width(300.dp)
            .height(170.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item?.translation?.let { Text(text = it, style = MaterialTheme.typography.h3) }
        }
    }
}

@Composable
fun DraggableKanjiCard(item: Kanji) {

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    IconToggleButton(
        checked = false,
        onCheckedChange = {
        },
        modifier = Modifier
            .width(60.dp)
            .height(60.dp)
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumed
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }

    ) {
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, color = Color.White),
            backgroundColor = Purple200,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.kanji, style = MaterialTheme.typography.h4)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FixedCardPreview() {
    FixedTranslation(
        item = Kanji(
            translation = "person",
            kanji = "人",
            id = 0,
            dateTranslated = 123
        )
    )
}

@Preview(showBackground = true)
@Composable
fun KanjiCardPreview() {
    DraggableKanjiCard(
        item = Kanji(
            translation = "person",
            kanji = "人",
            id = 0,
            dateTranslated = 123
        )
    )
}