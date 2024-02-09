package com.example.kanjimemory.screens

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.sharedComposables.TopBar
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.ui.theme.Purple700
import com.example.kanjimemory.viewmodel.RepetitionViewModel

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun RepetitionScreen(navController: NavController = rememberNavController()) {

    val repetitionViewModel: RepetitionViewModel = hiltViewModel()

    Scaffold(topBar = {
        TopBar(navController = navController)
    }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), color = MaterialTheme.colors.primary
        ) {

            val accessKanji = repetitionViewModel.randomKanji.collectAsState().value
            val context = LocalContext.current
            val vibrator = context.getSystemService(Vibrator::class.java)

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp), shape = RectangleShape
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (accessKanji != null) {
                            Text(text = accessKanji.kanji, fontSize = 100.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(20.dp))

                var textInput by remember { mutableStateOf("") }

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = textInput,
                    onValueChange = { textInput = it },
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Purple700),
                    placeholder = {
                        Text(
                            text = "Type here...",
                            color = Color.White
                        )
                    }
                )

                Spacer(modifier = Modifier.padding(20.dp))

                Button(onClick = {

                    repetitionViewModel.translationToCheck = textInput
                    repetitionViewModel.checkTranslation()

                    Toast.makeText(
                        context, repetitionViewModel.displayToast.value, Toast.LENGTH_SHORT
                    ).show()

                    vibrator.vibrate(repetitionViewModel.vibrationLong.value?.let { value ->
                        VibrationEffect.createOneShot(
                            value, VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    })

                    textInput = ""

                    repetitionViewModel.getOneRandomKanji()

                }, colors = ButtonDefaults.buttonColors(Purple200)) {
                    Text(text = "Check Translation")
                }
            }
        }
    }
}
