package com.example.kanjimemory.screens

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.sharedComposables.TopBar
import com.example.kanjimemory.viewmodel.KanjiViewModel

@Composable
fun KanjiListScreen(navController: NavController = rememberNavController()) {
    val kanjiViewModel: KanjiViewModel = hiltViewModel()
    val database = kanjiViewModel.kanjiList.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopBar(navController = navController)
    }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), color = MaterialTheme.colors.primary
        ) {
            LazyColumn {
                items(database.value) { item ->
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