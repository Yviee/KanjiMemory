package com.example.kanjimemory.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.navigation.KanjiScreens
import com.example.kanjimemory.ui.theme.KanjiMemoryTheme
import com.example.kanjimemory.ui.theme.Purple200

@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    KanjiMemoryTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary) {
            HomeMenu(navController = navController)
        }
    }
}

@Composable
fun HomeMenu(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {
        Text(text = "Welcome", fontSize = 40.sp, fontFamily = FontFamily.Cursive)
        Text(text = "to", fontSize = 40.sp, fontFamily = FontFamily.Cursive)
        Text(text = "Kanji Memory!", fontSize = 40.sp, fontFamily = FontFamily.Cursive)

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                navController.navigate(route = KanjiScreens.ExerciseScreen.name)
                         },
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier.height(80.dp)) {
            Text(text = "Start Kanji Exercise", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {navController.navigate(route = KanjiScreens.KanjiListScreen.name)},
            colors = ButtonDefaults.buttonColors(Purple200),
            modifier = Modifier.height(80.dp)) {
            Text(text = "Show all Kanjis", fontSize = 20.sp)
        }
    }
}
