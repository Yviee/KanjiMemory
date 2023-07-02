package com.example.kanjimemory.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.sharedComposables.DragTarget
import com.example.kanjimemory.sharedComposables.DraggableScreen
import com.example.kanjimemory.sharedComposables.DropItem
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.ui.theme.Purple500
import com.example.kanjimemory.viewmodel.DragDropViewModel
import kotlin.math.roundToInt

@Composable
fun DragDropTryoutScreen(navController: NavController = rememberNavController()) {

    val dragDropViewModel: DragDropViewModel = hiltViewModel()
    val kanjiList = dragDropViewModel.randomKanjiList.collectAsState().value
    val firstTranslationItem = kanjiList.firstOrNull()
    val kanjis = kanjiList.shuffled()

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

                //firstTranslationItem?.let { DropScreen(dragDropViewModel, it) }
                //DropScreen(dragDropViewModel, database)


                val screenWidth = LocalConfiguration.current.screenWidthDp

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ){
                        kanjis.forEach { kanji ->
                            // Todo look at DragTarget to find out why kanji data to drop is not updating
                            // try something like MutableStateOf<T>
                            DragTarget(
                                dataToDrop = kanji,
                                dragDropViewModel = dragDropViewModel
                            ) {
                                //DraggableKanjiCard(item = kanji)

                                Box (
                                    modifier = Modifier
                                        .size(Dp(screenWidth / 5f))
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Purple200, RoundedCornerShape(20.dp))
                                        .border(BorderStroke(2.dp, color = Color.White)),
                                    contentAlignment = Alignment.Center
                                )
                                {
                                    Text(
                                        text = kanji.kanji,
                                        style = MaterialTheme.typography.h4,
                                    )
                                }
                            }
                        }
                    }

                    val valueContext = LocalContext.current

                    // TODO: find out why it doesn't take the correct kanji item despite displaying them
                    // it's probably got something to do with .shuffled and / or how copies are made!
                    DropItem<Kanji>(
                        modifier = Modifier
                    ) { isInBound, kanjiItem ->
                        if(kanjiItem != null){
                            // only if user drops item, then kanjiItem (the data) will not be null
                            dragDropViewModel.checkIfMatch(kanjiItem)
                            Toast.makeText(
                                valueContext,
                                dragDropViewModel.displayToast.value,
                                Toast.LENGTH_SHORT)
                                .show()
                        }

                        FixedTranslation(
                            item = firstTranslationItem,
                            isInBound = isInBound
                        )
                    }
                }



                /*Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.padding(20.dp))

                    DropItem<Kanji>(
                        modifier = Modifier) { isInBound, kanjiItem ->
                        if (kanjiItem != null) {
                            // only if user drops item, then kanjiItem (the data) will not be null
                            LaunchedEffect(key1 = kanjiItem) {
                                dragDropViewModel.addKanji(kanjiItem)
                            }
                        }
                            FixedTranslation(
                                item = firstTranslationItem,
                                isInBound = isInBound
                            )

                    }
                    Spacer(modifier = Modifier.padding(20.dp))

                    // kanjis need to be below FixedTranslation for the moment, since they would otherwise
                    // get lost underneath it when dragged over
                    Row (
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        kanjis.forEach { kanji ->
                            DragTarget(dataToDrop = kanji, dragDropViewModel = dragDropViewModel) {
                                DraggableKanjiCard(item = kanji)
                            }
                        }
                    }


                    // using LazyGrid: https://stackoverflow.com/questions/64913067/reorder-lazycolumn-items-with-drag-drop

                    // https://blog.canopas.com/android-drag-and-drop-ui-element-in-jetpack-compose-14922073b3f1
                    // https://devblogs.microsoft.com/surface-duo/jetpack-compose-drag-and-drop/
                    // https://gist.github.com/surajsau/f5342f443352195208029e98b0ee39f3
                    // https://proandroiddev.com/basic-drag-n-drop-in-jetpack-compose-a6919ba58ba8
                    // TODO: find out why kanjis shuffle twice :S
                    // TODO: find out how to center text that goes over more than one line
                    // may have to put everything in Grid to be able to get dropCards to access fixed card
                    // implement something like: https://github.com/MatthiasKerat/DragAndDropYT/tree/main/app/src/main/java/com/kapps/draganddrop
                }*/
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
            item?.translation?.let { Text(text = it, style = MaterialTheme.typography.h3, textAlign = TextAlign.Center) }
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
    /*FixedTranslation(
        item = Kanji(
            translation = "person",
            kanji = "人",
            id = 0,
            dateTranslated = 123
        )
    )*/
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