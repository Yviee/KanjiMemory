package com.example.kanjimemory.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.navigation.KanjiScreens
import com.example.kanjimemory.ui.theme.KanjiMemoryTheme
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.ui.theme.Purple700

// In case Emulator is flickering: https://stackoverflow.com/questions/73349171/android-studio-avd-emulator-shows-a-black-flickering
@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    KanjiMemoryTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(),
            color = Purple700) {
            HomeMenu(navController = navController)
        }
    }
}

@Composable
fun HomeMenu(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
        Text(text = "Welcome", fontSize = 40.sp, fontFamily = FontFamily.Cursive, color = Color.White)
        Text(text = "to", fontSize = 40.sp, fontFamily = FontFamily.Cursive, color = Color.White)
        Text(text = "Kanji Memory!", fontSize = 40.sp, fontFamily = FontFamily.Cursive, color = Color.White)

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                navController.navigate(route = KanjiScreens.VibrationScreen.name)
            },
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier.height(80.dp)) {
            Text(text = "VibrationScreen", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                // used to be: KanjiScreens.DragDropScreen.name
                navController.navigate(route = KanjiScreens.DragDropTryoutScreen.name)
            },
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier.height(80.dp)) {
            Text(text = "Start Drag & Drop Exercise", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                navController.navigate(route = KanjiScreens.RepetitionScreen.name)
            },
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier.height(80.dp)) {
            Text(text = "Start Kanji Repetition", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                navController.navigate(route = KanjiScreens.ExerciseScreen.name)
                         },
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier.height(80.dp)) {
            Text(text = "Start Kanji Memory", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {navController.navigate(route = KanjiScreens.KanjiListScreen.name)},
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier
                .height(80.dp)
                .padding()
        ) {
            Text(text = "Show all Kanjis", fontSize = 20.sp, color = Color.White)
        }
    }
}
