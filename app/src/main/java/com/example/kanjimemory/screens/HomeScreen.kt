package com.example.kanjimemory.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.navigation.KanjiScreens
import com.example.kanjimemory.ui.theme.KanjiMemoryTheme
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.ui.theme.Purple500

// In case Emulator is flickering: https://stackoverflow.com/questions/73349171/android-studio-avd-emulator-shows-a-black-flickering
@Composable
fun HomeScreen(navController: NavController = rememberNavController()) {
    KanjiMemoryTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Purple500
        ) {
            HomeMenu(navController = navController)
        }
    }
}

@Composable
fun HomeMenu(navController: NavController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(5.dp)) {

        Headline(text = "Welcome\nto\nKanji Memory!")

        Spacer(modifier = Modifier.height(50.dp))

        /* HomeButton(onClick = {
             navController.navigate(route = KanjiScreens.VibrationScreen.name)
         }, text = "VibrationScreen")

         Spacer(modifier = Modifier.padding(10.dp))*/

        HomeButton(onClick = {
            navController.navigate(route = KanjiScreens.KanjiListScreen.name)
        }, text = "Show all Kanjis")

        Spacer(modifier = Modifier.padding(10.dp))

        HomeButton(onClick = {
            navController.navigate(route = KanjiScreens.ExerciseScreen.name)
        }, text = "Start Kanji Memory")

        Spacer(modifier = Modifier.padding(10.dp))

        HomeButton(onClick = {
            navController.navigate(route = KanjiScreens.RepetitionScreen.name)
        }, text = "Start Kanji Repetition")

        Spacer(modifier = Modifier.padding(10.dp))

        HomeButton(onClick = {
            navController.navigate(route = KanjiScreens.DragDropScreen.name)
        }, text = "Start Drag & Drop Exercise")
    }
}

@Composable
fun Headline(text: String) {
    Text(
        text = text,
        fontSize = 40.sp,
        fontFamily = FontFamily.Cursive,
        color = Color.White,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HomeButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Purple200),
        modifier = Modifier
            .height(80.dp)
    ) {
        Text(text = text, fontSize = 20.sp, color = Color.White)
    }
}
