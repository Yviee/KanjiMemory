package com.example.kanjimemory.sharedComposables

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kanjimemory.ui.theme.Purple200

@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Back to Main Menu",
                style = MaterialTheme.typography.subtitle1
            )
        },
        elevation = 3.dp,
        backgroundColor = Purple200,
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() },
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "BackArrow"
                    )
                }
            )
        }
    )
}