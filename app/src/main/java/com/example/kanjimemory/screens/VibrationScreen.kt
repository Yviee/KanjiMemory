package com.example.kanjimemory.screens

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.sharedComposables.TopBar

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun VibrationScreen(navController: NavController = rememberNavController()) {

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {


            val context = LocalContext.current

            val vibrator by lazy {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val vibratorManager =
                        context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                    vibratorManager.defaultVibrator
                } else {
                    @Suppress("DEPRECATION") context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                }
            }

            /*
         val vibrationEffects = listOf(
            VibrationEffect.Composition.PRIMITIVE_CLICK,
            VibrationEffect.Composition.PRIMITIVE_THUD,
            VibrationEffect.Composition.PRIMITIVE_SPIN,
            VibrationEffect.Composition.PRIMITIVE_QUICK_RISE,
            VibrationEffect.Composition.PRIMITIVE_SLOW_RISE,
            VibrationEffect.Composition.PRIMITIVE_QUICK_FALL,
            VibrationEffect.Composition.PRIMITIVE_TICK,
            VibrationEffect.Composition.PRIMITIVE_LOW_TICK)

        // working in array: 0, 4
         */

            val vibrationPrimitives = listOf(
                VibrationEffect.Composition.PRIMITIVE_CLICK,
                VibrationEffect.Composition.PRIMITIVE_SLOW_RISE
            )


            vibrator.vibrate(
                VibrationEffect.startComposition().addPrimitive(
                    VibrationEffect.Composition.PRIMITIVE_SLOW_RISE
                ).addPrimitive(
                    VibrationEffect.Composition.PRIMITIVE_CLICK
                ).compose()
            )



            vibrationPrimitives.forEach { vibrationEffect ->
                Button(onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(vibrationEffect))

                }) {
                    Text(text = "$vibrationEffect")
                }
            }

            /*val vibrationEffects = listOf(
            VibrationEffect.DEFAULT_AMPLITUDE,
            VibrationEffect.EFFECT_CLICK,
            VibrationEffect.EFFECT_DOUBLE_CLICK,
            VibrationEffect.EFFECT_HEAVY_CLICK,
            VibrationEffect.EFFECT_TICK

            // working in array: 1, 2, 3
        )*/

            val vibrationEffects = listOf(
                VibrationEffect.EFFECT_CLICK,
                VibrationEffect.EFFECT_DOUBLE_CLICK,
                VibrationEffect.EFFECT_HEAVY_CLICK
            )

            vibrationEffects.forEach { vibrationEffect ->
                Button(onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(vibrationEffect))

                }) {
                    Text(text = "$vibrationEffect")
                }
            }


            // create wave
            /*val longTimings: LongArray = longArrayOf(50, 50, 50, 50, 50, 100, 350, 25, 25, 25, 25, 200)
        val longAmplitudes: IntArray =
            intArrayOf(33, 51, 75, 113, 170, 255, 0, 38, 62, 100, 160, 255)
        val repeatIndex = -1 // Do not repeat.

        Button(onClick = {
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    longTimings,
                    longAmplitudes,
                    repeatIndex
                )
            )
        }) {
            Text(text = "Two Times VibrationEffect")
        }*/

            val longTimings: LongArray = longArrayOf(50, 50, 50, 50, 100, 350, 25, 25, 25, 25, 200)
            val longAmplitudes: IntArray =
                intArrayOf(33, 75, 113, 170, 255, 0, 38, 62, 100, 160, 255)
            val repeatIndex = -1 // Do not repeat.

            Button(onClick = {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        longTimings,
                        longAmplitudes,
                        repeatIndex
                    )
                )
            }) {
                Text(text = "Two Times VibrationEffect")
            }

            // another wave
            val timings: LongArray = longArrayOf(50, 50, 100, 50, 50)
            val amplitudes: IntArray = intArrayOf(64, 128, 255, 128, 64)
            val repeat = -1 // Do not repeat

            Button(onClick = {
                vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, repeat))
            }) {
                Text(text = "Short Custom VibrationEffect")
            }

            // the following are all working:

            Button(onClick = {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }) {
                Text(text = "Short VibrationEffect")
            }

            Button(onClick = {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        1000,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            }) {
                Text(text = "Long VibrationEffect")
            }

            val haptic = LocalHapticFeedback.current

            Button(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }) {
                Text(text = "HapticFeedback")
            }

            val view = LocalView.current

            Button(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }) {
                Text(text = "View HapticFeedback")
            }
        }
    }
}