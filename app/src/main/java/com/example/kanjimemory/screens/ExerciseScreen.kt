package com.example.kanjimemory.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.sharedComposables.KanjiColumn
import com.example.kanjimemory.sharedComposables.MemoryCard
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.KanjiViewModel

@ExperimentalMaterialApi
@Composable
fun ExerciseScreen(kanjiViewModel: KanjiViewModel, navController: NavController = rememberNavController()) {

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
            }) {
            Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.primary) {


                /*val randomKanjiList = IntArray(5) {
                    database.random().id
                    }*/

                // With println, list is displayed in console.
                // Note: randomKanjiList is being randomised twice -> because of the way hierarchy is built?
                // Question: Should shuffling happen at higher level instead of inside of Composable?
                /*val randomKanjiList = database.shuffled().take(5).toList()

                for (i in randomKanjiList) {
                    println(i)
                }*/

                // val database = kanjiViewModel.randomKanjiList.collectAsState().value
                // Wanna make it interesting? Uncomment below
                // kanjiViewModel.getRandomKanjis()

                /*
                Row {
                    Column {


                        for (item in database) {
                            //var randomKanji = database.random().id
                            Text(text = item.kanji,
                                style = MaterialTheme.typography.h2)
                            // database.get(randomKanjiList[i]).kanji
                            //if (randomKanji == randomKanji) {
                            //    randomKanji = database.random().id
                            //}
                        }


                    }
                    Column {
                        for (i in database) {
                            //var randomKanji = database.random().id
                            Text(text = i.translation,
                                style = MaterialTheme.typography.h2)
                            // database.get(randomKanjiList[i]).kanji
                            //if (randomKanji == randomKanji) {
                            //    randomKanji = database.random().id
                            //}
                        }

                    }
                }

                 */

                MemoryGrid(kanjiViewModel = kanjiViewModel)

                //MemoryCard(kanjiViewModel)
                //Memory()

        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MemoryGrid (kanjiViewModel: KanjiViewModel) {

    val database = kanjiViewModel.randomKanjiList.collectAsState().value
    //kanjiViewModel.getRandomKanjis()

    LazyColumn {
        items(database) { kanjis ->
            //Text(kanjis.kanji)

            Row {
                // somehow there's no connection between Kanji model and Kanji DB in below line..?
                // KanjiCard(kanji = kanjis.kanji)
                Card(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .width(200.dp)
                        .height(130.dp)
                        .padding(20.dp),
                    border = BorderStroke(2.dp, color = Color.White),
                    backgroundColor = Purple200,
                    onClick = {}) {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(kanjis.kanji, style = MaterialTheme.typography.h3)
                    }
                }

                Card(modifier = Modifier
                    .width(200.dp)
                    .height(130.dp)
                    .padding(20.dp),
                    elevation = 20.dp,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(2.dp, color = Color.White),
                    backgroundColor = Purple200,
                    onClick = {}) {
                    Column(modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(kanjis.translation,
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun KanjiCard(kanji: Kanji) {

    // should Card content be an observable (remember)? -> when all cards have been matched,
    // a new set of cards with different content should be shown.

    Card(modifier = Modifier
        .width(200.dp)
        .height(130.dp)
        .padding(20.dp)
        .clickable { },
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, color = Color.White),
        backgroundColor = Purple200) {
        Column (modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(kanji.kanji, style = MaterialTheme.typography.h4)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryPreview() {
    //MemoryGrid()
}