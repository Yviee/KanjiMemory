package com.example.kanjimemory.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.RepetitionViewModel

@Composable
fun RepetitionScreen(navController: NavController = rememberNavController()) {

    val repetitionviewModel: RepetitionViewModel = hiltViewModel()

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

            // get random kanji from DB, show it on quarter-half the screen
            // text field to write in underneath
            // "check" button underneath
        }
    }
}
