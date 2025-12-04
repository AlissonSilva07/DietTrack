package com.edu.diettrack.presentation.ui.screens.onboarding.steps

import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.FadeInItem
import com.edu.diettrack.presentation.components.IconBg
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreenState

@Composable
fun OnboardingFinalStep(
    modifier: Modifier = Modifier,
    state: OnboardingScreenState
) {
    val decodedImageBytes by remember(state.profilePicture) {
        mutableStateOf(
            state.profilePicture?.let { Base64.decode(it, Base64.DEFAULT) }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FadeInItem(delay = 1000L) {
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
        }
        Spacer(Modifier.height(16.dp))
        FadeInItem(delay = 1000L) {
            Text(
                text = "Obrigado, ${state.name.text ?: "Usuário(a)"}!",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(16.dp))
        FadeInItem(delay = 1500L) {
            Text(
                text = "Aproveite seu app",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(16.dp))
        FadeInItem(delay = 2000L) {
            Image(
                painter = painterResource(R.drawable.logo_color),
                contentDescription = null,
                modifier = Modifier.width(240.dp)
            )
        }
    }
}