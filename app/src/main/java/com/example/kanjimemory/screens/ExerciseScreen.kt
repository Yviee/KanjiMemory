package com.example.kanjimemory.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.ExerciseViewModel
import java.util.Collections.shuffle

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExerciseScreen(navController: NavController = rememberNavController()) {
        val exerciseViewModel: ExerciseViewModel = hiltViewModel()

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

                // needs to be instantiated here because the shuffled function would shuffle all
                // kanjis on each recomposition (each change inside cards)
                val database = exerciseViewModel.randomKanjiList.collectAsState().value
                val kanjis = database.shuffled()

                MemoryGrid(kanjisShuffled = kanjis, translations = database, exerciseViewModel = exerciseViewModel)
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MemoryGrid (kanjisShuffled: List<Kanji>, translations: List<Kanji>, exerciseViewModel: ExerciseViewModel) {

    //val database = exerciseViewModel.randomKanjiList.collectAsState().value
    //val kanjis = database.shuffled()    // shuffles kanjis on each recomposition -> leads to shifting cards

    val solvedKanjis = exerciseViewModel.solvedKanjis.observeAsState()  // observe the solved kanjis state

    Box(contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column {
                kanjisShuffled.forEach { kanji ->
                    // entweder hier oder statt exerciseViewModel als Parameter übergeben, ob kanji selected ist oder nicht -> aus ViewModel
                    // see movie app: isFavourite
                    // exerciseViewModel.isSelected()
                    // (exerciseViewModel.disableCard(kanjiId = kanji.id)
                    // val disableClick = exerciseViewModel.cardClicked.observeAsState(true)
                    KanjiCard(
                        kanji = kanji,
                        solvedKanjis = solvedKanjis.value,  // pass solved kanjis state
                        onItemClick = {
                        exerciseViewModel.cardClicked.value = !(exerciseViewModel.cardClicked.value)!!
                        exerciseViewModel.addToSelected(kanjiId = kanji.id)
                        exerciseViewModel.reload()
                    })
                }
            }
            Column {
                translations.forEach { translation ->
                    TranslationCard(translation = translation, exerciseViewModel = exerciseViewModel, onItemClick = {
                        /*exerciseViewModel.translationClicked.value = true
                        exerciseViewModel.translationIdClicked.value = translation.id*/
                        //exerciseViewModel.checkMatch()
                        //totalGuesses++
                        exerciseViewModel.cardClicked.value = !(exerciseViewModel.cardClicked.value)!!
                        exerciseViewModel.addToSelected(kanjiId = translation.id)
                        //exerciseViewModel.translationClicked.value = !exerciseViewModel.translationClicked.value!!
                        exerciseViewModel.reload()
                    })
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun KanjiCard(kanji: Kanji,
              solvedKanjis: List<Int>?,
              onItemClick: () -> Unit = {}) {

    IconToggleButton(
        checked = false,
        onCheckedChange = {
            onItemClick()
        },
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .padding(20.dp),
        enabled = !solvedKanjis!!.contains(kanji.id)) { // check if THIS kanjis is inside solved kanjis -> if so disable
        //val tint by animateColorAsState(if (kanjiSelected) Color(0xFF8E60BE) else Purple200)

        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, color = Color.White),
            backgroundColor = Purple200,
            // tint was used in backgroundColor
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(kanji.kanji, style = MaterialTheme.typography.h4)
            }
        }
    }
}

    // https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#IconToggleButton(kotlin.Boolean,kotlin.Function1,androidx.compose.ui.Modifier,kotlin.Boolean,androidx.compose.foundation.interaction.MutableInteractionSource,kotlin.Function0)


@ExperimentalMaterialApi
@Composable
fun TranslationCard(translation: Kanji,  exerciseViewModel: ExerciseViewModel, onItemClick: () -> Unit = {}) {

    //val selection = exerciseViewModel.cardClicked.observeAsState(false)
    var translationSelected by remember { mutableStateOf(false) }

    // exerciseViewModel.cardClicked.observeAsState(false)
    val disableClick = exerciseViewModel.disableCard(kanjiId = translation.id)

    //var disableClick by remember { mutableStateOf(exerciseViewModel.disableCard(kanjiId = translation.id)) }
    //val selectCount by remember { mutableStateOf(exerciseViewModel.selectCount.value)}
    //val disableClick = exerciseViewModel.kanjiEnabled.observeAsState(true)

    IconToggleButton(
        checked = translationSelected,
        onCheckedChange = {
            translationSelected = it
            onItemClick()
        },
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .padding(20.dp),
        enabled = disableClick) {
        //val tint by animateColorAsState(if (translationSelected) Color(0xFF8E60BE) else Purple200)
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, color = Color.White),
            backgroundColor = Purple200,
            // tint was used in backgroundColor
        ) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(translation.translation, style = MaterialTheme.typography.h4)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MemoryPreview() {
    //MemoryGrid()
}