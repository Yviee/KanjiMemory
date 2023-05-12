package com.example.kanjimemory.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.screens.*
import com.example.kanjimemory.sharedComposables.DropScreen

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

        composable(KanjiScreens.DragDropScreen.name) {
            DragDropScreen(navController = navController)
        }

    }

}