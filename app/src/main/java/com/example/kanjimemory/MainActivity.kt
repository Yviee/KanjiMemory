package com.example.kanjimemory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kanjimemory.navigation.KanjiNavigation
import com.example.kanjimemory.ui.theme.KanjiMemoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // AndroidEntryPoint identifies MainActivity as Dependency Container -> get dependency in Main Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KanjiNavigation()
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}