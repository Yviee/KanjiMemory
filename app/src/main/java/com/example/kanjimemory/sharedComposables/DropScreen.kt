package com.example.kanjimemory.sharedComposables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.screens.DraggableKanjiCard
import com.example.kanjimemory.screens.FixedTranslation
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.DragDropViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DropScreen(kanjiList: List<Kanji>, dragDropViewModel: DragDropViewModel, firstItem: Kanji) {

    // item size now depends on size of screen
    val screenWidth = LocalConfiguration.current.screenWidthDp

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            kanjiList.forEach { kanji ->
                DragTarget(
                    dataToDrop = kanji,
                    dragDropViewModel = dragDropViewModel
                ) {
                    //DraggableKanjiCard(item = kanji)

                    Box (
                        modifier = Modifier
                            .size(Dp(screenWidth / 5f))
                            .clip(RoundedCornerShape(20.dp))
                            .background(Purple200, RoundedCornerShape(20.dp))
                            .border(BorderStroke(2.dp, color = Color.White)),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = kanji.kanji,
                            style = MaterialTheme.typography.h4,
                        )
                    }
                    /*Box (
                        modifier = Modifier
                            .size(Dp(screenWidth / 5f))
                            .clip(RoundedCornerShape(15.dp))
                            .background(Purple200, RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                            )
                    {
                        Text(
                            text = kanji.kanji,
                            style = MaterialTheme.typography.body1,
                        )
                    }*/
                }
            }
        }

        // TODO: find out why it doesn't take the correct kanji item despite displaying them
        // it's probably got something to do with .shuffled and / or how copies are made!
        DropItem<Kanji>(
            modifier = Modifier
                //.size(Dp(screenWidth / 3.5f))
        ) { isInBound, kanjiItem ->
            if(kanjiItem != null){
                // only if user drops item, then kanjiItem (the data) will not be null
                    dragDropViewModel.checkIfMatch(kanjiItem)
            }

            FixedTranslation(
                item = firstItem,
                isInBound = isInBound
            )
           /* if(isInBound){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, color = Color.Red, shape = RoundedCornerShape(15.dp))
                        .background(Color.Gray.copy(0.5f), RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = kanjiList.firstOrNull()?.translation!!,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }else{
                // if item is dragged, but not in bound, then box should be shown differently
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(1.dp, color = Color.White, shape = RoundedCornerShape(15.dp))
                        .background(Color.Black.copy(0.6f), RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = kanjiList.firstOrNull()?.translation!!,
                        style = MaterialTheme.typography.body1,
                    )
                }
            }*/
        }

        // composable for dropTarget should show clicked when dragItem is dragged
        // TODO: use this logic for fixed translation, but without animated visibility
        // should only show up if item is currently dragging
        /*AnimatedVisibility(
            dragDropViewModel.isCurrentlyDragging,
            enter = slideInHorizontally (initialOffsetX = {it})
        ) {
            DropItem<Kanji>(
                modifier = Modifier
                    .size(Dp(screenWidth / 3.5f))
            ) { isInBound, kanjiItem ->
                if(kanjiItem != null){
                    // only if user drops item, then kanjiItem (the data) will not be null
                    LaunchedEffect(key1 = kanjiItem){
                        dragDropViewModel.addKanji(kanjiItem)
                    }
                }
                if(isInBound){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, color = Color.Red, shape = RoundedCornerShape(15.dp))
                            .background(Color.Gray.copy(0.5f), RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Kanji",
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }else{
                    // if item is dragged, but not in bound, then box should be shown differently
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .border(1.dp, color = Color.White, shape = RoundedCornerShape(15.dp))
                            .background(Color.Black.copy(0.6f), RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Add Kanji",
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
        }*/
    }
}