package com.example.kanjimemory.sharedComposables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.screens.FixedTranslation
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.DragDropViewModel

@Composable
fun DropScreen(dragDropViewModel: DragDropViewModel, kanjiList: List<Kanji>) {

    // , firstItem: Kanji
    // item size now depends on size of screen
    val screenWidth = LocalConfiguration.current.screenWidthDp

    //val kanjiList = dragDropViewModel.randomKanjiList.value
    val firstItem = dragDropViewModel.randomKanjiList.value.firstOrNull()

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
                // Todo look at DragTarget to find out why kanji data to drop is not updating
                // try something like MutableStateOf<T>
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
                }
            }
        }

        // TODO: find out why it doesn't take the correct kanji item despite displaying them
        // it's probably got something to do with .shuffled and / or how copies are made!
        DropItem<Kanji>(
            modifier = Modifier
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
    }
}