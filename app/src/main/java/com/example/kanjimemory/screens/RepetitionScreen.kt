package com.example.kanjimemory.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.RepetitionViewModel

@Composable
fun RepetitionScreen(navController: NavController = rememberNavController()) {

    val repetitionViewModel: RepetitionViewModel = hiltViewModel()

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

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val accessKanji = repetitionViewModel.randomKanji.collectAsState().value
                val context = LocalContext.current

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                        /*for (item in accessKanji) {
                            Text(text = item.kanji, fontSize = 100.sp)
                        }*/
                        if (accessKanji != null) {
                            Text(text = accessKanji.kanji, fontSize = 100.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(20.dp))

                var textInput by remember { mutableStateOf(TextFieldValue("")) }
                TextField(modifier = Modifier.fillMaxWidth(),
                    value = textInput,
                    onValueChange = { textInput = it },
                    maxLines = 1)

                Spacer(modifier = Modifier.padding(20.dp))

                Button(onClick = {

                    repetitionViewModel.translationToCheck.value = textInput.text
                    repetitionViewModel.checkTranslation()
                    Toast.makeText(context, repetitionViewModel.displayToast.value, Toast.LENGTH_SHORT).show()
                    repetitionViewModel.getOneRandomKanji()
                    textInput = TextFieldValue("")

                }, colors = ButtonDefaults.buttonColors(Purple200)) {
                    Text(text = "Check Translation")
                }

            }
        }
    }
}
