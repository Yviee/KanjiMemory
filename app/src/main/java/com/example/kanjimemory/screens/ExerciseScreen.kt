package com.example.kanjimemory.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.sharedComposables.TopBar
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.ExerciseViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExerciseScreen(navController: NavController = rememberNavController()) {
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()

    Scaffold(topBar = {
        TopBar(navController = navController)
    }) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.primary
        ) {
            val database = exerciseViewModel.randomKanjiList.collectAsState().value

            val kanjis = database.shuffled()

            MemoryGrid(
                kanjisShuffled = kanjis,
                translations = database,
                exerciseViewModel = exerciseViewModel
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MemoryGrid(
    kanjisShuffled: List<Kanji>,
    translations: List<Kanji>,
    exerciseViewModel: ExerciseViewModel,
) {

    val solvedKanjis =
        exerciseViewModel.solvedKanjis.observeAsState()  // observe solved kanjis as state

    Box(contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column {
                kanjisShuffled.forEach { kanji ->
                    MemoryCard(kanjiObject = kanji,
                        word = kanji.kanji,
                        solvedKanjis = solvedKanjis.value,
                        onItemClick = {
                            exerciseViewModel.kanjiCardClicked.value = true
                            exerciseViewModel.addToSelected(kanjiId = kanji.id)
                            exerciseViewModel.reload()
                        })
                }

            }
            Column {
                translations.forEach { translation ->
                    MemoryCard(kanjiObject = translation,
                        word = translation.translation,
                        solvedKanjis = solvedKanjis.value,
                        onItemClick = {
                            exerciseViewModel.translationCardClicked.value = true
                            exerciseViewModel.addToSelected(kanjiId = translation.id)
                            exerciseViewModel.reload()
                        })
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MemoryCard(
    kanjiObject: Kanji,
    word: String,
    solvedKanjis: List<Int>?,
    onItemClick: () -> Unit = {},
) {

    IconToggleButton(
        checked = false,
        onCheckedChange = {
            onItemClick()
        },
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .padding(20.dp),
        enabled = !solvedKanjis!!.contains(kanjiObject.id)
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
                Text(text = word, style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
            }
        }
    }
}