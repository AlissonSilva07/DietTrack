package com.edu.diettrack.presentation.ui.screens.onboarding.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.FadeInItem

@Composable
fun OnboardingFinalStep(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FadeInItem(delay = 1000L) {
            Text(
                text = "Obrigado!",
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