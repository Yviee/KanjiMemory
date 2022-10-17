package com.example.kanjimemory.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.screens.ExerciseScreen
import com.example.kanjimemory.screens.HomeScreen
import com.example.kanjimemory.screens.KanjiListScreen
import com.example.kanjimemory.screens.RepetitionScreen

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun KanjiNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = KanjiScreens.HomeScreen.name) {
        composable(KanjiScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(KanjiScreens.ExerciseScreen.name) {
            ExerciseScreen(navController = navController)
        }

        composable(KanjiScreens.KanjiListScreen.name) {
            KanjiListScreen(navController = navController)
        }

        composable(KanjiScreens.RepetitionScreen.name) {
            RepetitionScreen(navController = navController)
        }
    }

}