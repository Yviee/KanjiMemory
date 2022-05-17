package com.example.kanjimemory.sharedComposables

import android.view.Surface
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.KanjiViewModel


@Composable
fun MemoryCard(kanjiViewModel: KanjiViewModel) {

    val database = kanjiViewModel.kanjiList.collectAsState().value

    Card(modifier = Modifier
        .width(200.dp)
        .height(100.dp),
        elevation = 20.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, color = Color.White),
        backgroundColor = Purple200) {

        KanjiColumn(kanjiViewModel = kanjiViewModel)
    }
}

@Composable
fun KanjiColumn(kanjiViewModel: KanjiViewModel) {

    /*val database = kanjiViewModel.kanjiList.collectAsState().value

    LazyColumn {
        items(5) { database.shuffled()}
    }*/

    /*
    var kanjiSelection by remember {
            // mutableList? --> choose 5 random kanjis and put into list.
            mutableStateListOf(kanjiViewModel.kanji.random(5))
    }

    LazyColumn {
            items(kanjiSelection) {
                    kanji -> MemoryCard()
            }
    }

     */
}

@Composable
fun TranslationColumn(translation: Kanji) {

}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    //MemoryCard()
}