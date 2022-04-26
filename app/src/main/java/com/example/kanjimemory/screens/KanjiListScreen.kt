package com.example.kanjimemory.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.KanjiViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun KanjiListScreen (kanjiViewModel: KanjiViewModel, navController: NavController = rememberNavController()) {

    val database = kanjiViewModel.kanjiList.collectAsState().value
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

            Column() {
                for (item in database) {
                    Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                        Text(
                            modifier = Modifier.weight(2f, true),
                            text = item.kanji)

                        Text(
                            modifier = Modifier.weight(2f, true),
                            text = item.translation)
                    }
                }
            }
        }
    }
}

/*
fun KanjiList(kanjis: StateFlow<List<Kanji>>) {

}

 */
/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        elevation = 10.dp) {
            Column(modifier = Modifier.padding(10.dp)) {
            }
        }
}

 */