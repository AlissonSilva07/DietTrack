package com.edu.diettrack.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.diettrack.R

@Composable
fun AvatarField(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        IconBg(
            bgSize = 143.dp,
            content = {
                Text(
                    text = "🙋🏻‍♀️",
                    fontSize = 64.sp
                )
            }
        )
        Button(
            onClick = {},
            contentPadding = PaddingValues(vertical = 2.dp, horizontal = 2.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.photo_camera_24px),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}