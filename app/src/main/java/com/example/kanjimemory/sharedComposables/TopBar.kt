package com.example.kanjimemory.sharedComposables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kanjimemory.ui.theme.Purple200

@Composable
fun TopBar(navController: NavController) {
    TopAppBar(elevation = 3.dp, backgroundColor = Purple200) {
        Row {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Arrow back",
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(
                        onClick = { navController.popBackStack() },
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() })
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Back to Main Menu",
                style = MaterialTheme.typography.subtitle1
            )
        }
    }
}