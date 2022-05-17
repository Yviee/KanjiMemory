package com.example.kanjimemory.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.screens.ExerciseScreen
import com.example.kanjimemory.screens.HomeScreen
import com.example.kanjimemory.screens.KanjiListScreen
import com.example.kanjimemory.viewmodel.KanjiViewModel

@Composable
fun KanjiNavigation() {

    val navController = rememberNavController()
    val kanjiViewModel: KanjiViewModel = viewModel()

    NavHost(navController = navController, startDestination = KanjiScreens.HomeScreen.name) {
        composable(KanjiScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(KanjiScreens.ExerciseScreen.name) {
            ExerciseScreen(kanjiViewModel = kanjiViewModel, navController = navController)
        }

        composable(KanjiScreens.KanjiListScreen.name) {
            KanjiListScreen(kanjiViewModel = kanjiViewModel, navController = navController)
        }
    }


}