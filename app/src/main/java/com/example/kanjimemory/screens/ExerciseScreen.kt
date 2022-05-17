package com.example.kanjimemory.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.sharedComposables.KanjiColumn
import com.example.kanjimemory.sharedComposables.MemoryCard
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.KanjiViewModel

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

                val database = kanjiViewModel.kanjiList.collectAsState().value
                /*val randomKanjiList = IntArray(5) {
                    database.random().id
                    }*/

                // With println, list is displayed in console.
                // Note: randomKanjiList is being randomised twice -> because of the way hierarchy is built?
                // Question: Should shuffling happen at higher level instead of inside of Composable?
                val randomKanjiList = database.shuffled().take(5).toList()

                for (i in randomKanjiList) {
                    println(i)
                }
                /*Row {
                    Column {

                        for (i in 0..4) {
                            //var randomKanji = database.random().id
                            Text(text = randomKanjiList[i].kanji,
                                style = MaterialTheme.typography.h2)
                            // database.get(randomKanjiList[i]).kanji
                            //if (randomKanji == randomKanji) {
                            //    randomKanji = database.random().id
                            //}
                        }


                    }
                    Column {
                        for (i in 0..4) {
                            //var randomKanji = database.random().id
                            Text(text = randomKanjiList[i].translation,
                                style = MaterialTheme.typography.h2)
                            // database.get(randomKanjiList[i]).kanji
                            //if (randomKanji == randomKanji) {
                            //    randomKanji = database.random().id
                            //}
                        }

                    }
                }
*/
                //MemoryGrid()



                //MemoryCard(kanjiViewModel)
                //Memory()

        }
    }
}

@Composable
fun MemoryGrid () {

    Row {

        LazyColumn() {
            items(5) {
                Card(modifier = Modifier
                    .width(200.dp)
                    .height(130.dp)
                    .padding(20.dp),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(2.dp, color = Color.White),
                    backgroundColor = Purple200) {

                }
            }
        }

        LazyColumn() {
            items(5) {
                Card(modifier = Modifier
                    .width(200.dp)
                    .height(130.dp)
                    .padding(20.dp),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(2.dp, color = Color.White),
                    backgroundColor = Purple200) {

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoryPreview() {
    MemoryGrid()
}