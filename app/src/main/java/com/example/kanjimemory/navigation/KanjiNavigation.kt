package com.example.kanjimemory.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.screens.DragDropScreen
import com.example.kanjimemory.screens.ExerciseScreen
import com.example.kanjimemory.screens.HomeScreen
import com.example.kanjimemory.screens.KanjiListScreen
import com.example.kanjimemory.screens.RepetitionScreen
import com.example.kanjimemory.screens.VibrationScreen
import com.example.kanjimemory.viewmodel.DragDropViewModel
import com.example.kanjimemory.viewmodel.ExerciseViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun KanjiNavigation() {

    val navController = rememberNavController()

    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    val database = exerciseViewModel.randomKanjiList.collectAsStateWithLifecycle().value
    val exerciseKanjis = database.shuffled()

    val dragDropViewModel: DragDropViewModel = hiltViewModel()
    val kanjiList = dragDropViewModel.randomKanjiList.collectAsStateWithLifecycle().value
    val firstTranslationItem = kanjiList.firstOrNull()
    val dragDropKanjis = kanjiList.shuffled()

    NavHost(navController = navController, startDestination = KanjiScreens.HomeScreen.name) {
        composable(KanjiScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(KanjiScreens.ExerciseScreen.name) {
            ExerciseScreen(
                navController = navController,
                exerciseViewModel,
                database,
                exerciseKanjis
            )
        }

        composable(KanjiScreens.KanjiListScreen.name) {
            KanjiListScreen(navController = navController)
        }

        composable(KanjiScreens.RepetitionScreen.name) {
            RepetitionScreen(navController = navController)
        }

        composable(KanjiScreens.DragDropScreen.name) {
            DragDropScreen(
                navController = navController,
                dragDropViewModel,
                firstTranslationItem,
                dragDropKanjis
            )
        }

        composable(KanjiScreens.VibrationScreen.name) {
            VibrationScreen(navController = navController)
        }
    }
}