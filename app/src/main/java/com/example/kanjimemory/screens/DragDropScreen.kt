package com.example.kanjimemory.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.DragDropViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DragDropScreen(navController: NavController = rememberNavController()) {

    val dragDropViewModel: DragDropViewModel = hiltViewModel()

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
        })
    {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {
            Column {
                Spacer(modifier = Modifier.padding(20.dp))

                val database = dragDropViewModel.randomKanjiList.collectAsState().value
                val firstTranslationItem = database.firstOrNull()
                val kanjis = database.shuffled()

                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        FixedTranslation(item = firstTranslationItem)
                    }
                }

                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    items(kanjis) { kanji ->
                        KanjiCard(item = kanji)
                    }
                }
            }
        }
    }
}

@Composable
fun FixedTranslation(item: Kanji?) {
    Card(
        elevation = 10.dp,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(2.dp, color = Color.White),
        backgroundColor = Purple200,
        modifier = Modifier
            .width(300.dp)
            .height(170.dp)
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item?.translation?.let { Text(text = it, style = MaterialTheme.typography.h3) }
        }
    }
}

@Composable
fun KanjiCard(item: Kanji) {

    IconToggleButton(
        checked = false,
        onCheckedChange = {
        },
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(20.dp)
            //.draggable()
    ) {
        Card(
            elevation = 10.dp,
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(2.dp, color = Color.White),
            backgroundColor = Purple200,
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = item.kanji, style = MaterialTheme.typography.h4)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FixedCardPreview() {
    FixedTranslation(
        item = Kanji(
            translation = "person",
            kanji = "人",
            id = 0,
            dateTranslated = 123
        )
    )
}

@Preview(showBackground = true)
@Composable
fun KanjiCardPreview() {
    KanjiCard(item = Kanji(translation = "person", kanji = "人", id = 0, dateTranslated = 123))
}