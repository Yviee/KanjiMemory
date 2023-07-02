package com.example.kanjimemory.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.kanjimemory.model.Kanji
import com.example.kanjimemory.ui.theme.Purple200
import com.example.kanjimemory.viewmodel.ExerciseViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ExerciseScreen(navController: NavController = rememberNavController()) {
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()

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
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.primary
        ) {
            // TODO: collectAsStateWithLifecycle() -> update dependencies!
            // https://blog.protein.tech/exploring-differences-collectasstate-collectasstatewithlifecycle-7fde491110c0
            val database = exerciseViewModel.randomKanjiList.collectAsState().value

            val kanjis = database.shuffled()

            MemoryGrid(
                kanjisShuffled = kanjis,
                translations = database,
                exerciseViewModel = exerciseViewModel
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MemoryGrid(
    kanjisShuffled: List<Kanji>,
    translations: List<Kanji>,
    exerciseViewModel: ExerciseViewModel,
) {

    val solvedKanjis =
        exerciseViewModel.solvedKanjis.observeAsState()  // observe the solved kanjis state
    val context = LocalContext.current

    Box(contentAlignment = Alignment.Center) {
        Row(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column {
                kanjisShuffled.forEach { kanji ->
                    MemoryCard(
                        kanji = kanji,
                        word = kanji.kanji,
                        solvedKanjis = solvedKanjis.value,
                        onItemClick = {
                            exerciseViewModel.kanjiCardClicked.value =
                                !(exerciseViewModel.kanjiCardClicked.value)!!
                            exerciseViewModel.addToSelected(kanjiId = kanji.id)
                            exerciseViewModel.reload()
                        })
                }

            }
            Column {
                translations.forEach { translation ->
                    MemoryCard(
                        kanji = translation,
                        word = translation.translation,
                        solvedKanjis = solvedKanjis.value,
                        onItemClick = {
                            exerciseViewModel.translationCardClicked.value =
                                !(exerciseViewModel.translationCardClicked.value)!!
                            exerciseViewModel.addToSelected(kanjiId = translation.id)
                            exerciseViewModel.reload()
                        })
                }
            }
            Toast.makeText(
                context,
                exerciseViewModel.displayToast.value,
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MemoryCard(
    kanji: Kanji,
    word: String,
    solvedKanjis: List<Int>?,
    onItemClick: () -> Unit = {},
) {

    IconToggleButton(
        checked = false,
        onCheckedChange = {
            onItemClick()
        },
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .padding(20.dp),
        enabled = !solvedKanjis!!.contains(kanji.id)
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
                Text(text = word, style = MaterialTheme.typography.h4)
            }
        }
    }
}