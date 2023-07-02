package com.example.kanjimemory.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.RepetitionViewModel

@RequiresApi(Build.VERSION_CODES.R)
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

            val accessKanji = repetitionViewModel.randomKanji.collectAsState().value
            val context = LocalContext.current
            val vibrator by lazy {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    vibratorManager.defaultVibrator
                }
                else {
                    @Suppress("DEPRECATION")
                    context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                }
            }
            // Vibrator does not work!!
            /*val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
             */
            /*val vibration = VibrationEffect.startComposition()
                .addPrimitive(VibrationEffect.Composition.PRIMITIVE_SPIN, 0.5f)
                .compose()*/

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                val valueContext = LocalContext.current

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
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

                val haptic = LocalHapticFeedback.current
                val view = LocalView.current

                Button(onClick = {

                    repetitionViewModel.translationToCheck = textInput.text
                    repetitionViewModel.checkTranslation()

                    Toast.makeText(
                        valueContext,
                        repetitionViewModel.displayToast.value,
                        Toast.LENGTH_SHORT)
                        .show()

                    //haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                    // for both view and haptic only long press works!
                    //view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)

                    //vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                    // correctTranslation -> vibrate short for correct, long for incorrect
                    // should be viewModel value, which is updated during check() function
                    /*
                    if (correctTranslation) {
                    vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
                    } else {
                    vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))}
                     */

                    textInput = TextFieldValue("")

                    repetitionViewModel.getOneRandomKanji()

                }, colors = ButtonDefaults.buttonColors(Purple200)) {
                    Text(text = "Check Translation")
                }
            }
        }
    }
}
