package com.example.kanjimemory.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.sharedComposables.TopBar
import com.example.kanjimemory.viewmodel.KanjiViewModel

@Composable
fun KanjiListScreen(navController: NavController = rememberNavController()) {
    val kanjiViewModel: KanjiViewModel = hiltViewModel()
    val database = kanjiViewModel.kanjiList.collectAsState()

    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {
            LazyColumn {
                itemsIndexed(database.value) { _, item: Kanji ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                    ) {

                        Text(
                            modifier = Modifier.weight(2f, true),
                            text = item.kanji,
                            style = MaterialTheme.typography.h4
                        )

                        Text(
                            modifier = Modifier.weight(2f, true),
                            text = item.translation,
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}