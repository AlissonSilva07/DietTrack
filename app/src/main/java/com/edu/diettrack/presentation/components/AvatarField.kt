package com.edu.diettrack.presentation.components

import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.edu.diettrack.R
import com.edu.diettrack.presentation.ui.theme.AppTheme
import java.io.InputStream

@Composable
fun AvatarField(
    modifier: Modifier = Modifier,
    base64Image: String?,
    onImagePicked: (image: String) -> Unit
) {
    val context = LocalContext.current

    val decodedImageBytes by remember(base64Image) {
        mutableStateOf(
            base64Image?.let { Base64.decode(it, Base64.DEFAULT) }
        )
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult

        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

        inputStream?.use { stream ->
            val bytes = stream.readBytes()

            val base64 = Base64.encodeToString(bytes, Base64.DEFAULT)
            onImagePicked(base64)
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomEnd
    ) {
        IconBg(
            bgSize = 142.dp,
            content = {
                if (decodedImageBytes != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .size(88.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = decodedImageBytes,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                } else {
                    Text(
                        text = "🙋🏻‍♀️",
                        fontSize = 64.sp
                    )
                }
            }
        )

        Button(
            onClick = { imagePickerLauncher.launch("image/*") },
            contentPadding = PaddingValues(vertical = 2.dp, horizontal = 2.dp)
        ) {
            if (decodedImageBytes != null) {
                Icon(
                    painter = painterResource(R.drawable.edit_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.photo_camera_24px),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AvatarFieldPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        AvatarField(
            base64Image = null,
            onImagePicked = { }
        )
    }
}
