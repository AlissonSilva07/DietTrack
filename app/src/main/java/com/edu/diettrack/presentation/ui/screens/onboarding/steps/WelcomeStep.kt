package com.edu.diettrack.presentation.ui.screens.onboarding.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.FadeInItem
import com.edu.diettrack.presentation.components.IconBg

@Composable
fun WelcomeStep(modifier: Modifier = Modifier) {
    val isDarkTheme = isSystemInDarkTheme()
    val arrowLogo = if (isDarkTheme) R.drawable.arrow_light else R.drawable.arrow_light

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FadeInItem(delay = 500L) {
                Text(
                    text = "Boas-vindas ao",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            FadeInItem(delay = 1000L) {
                Image(
                    painter = painterResource(R.drawable.logo_color),
                    contentDescription = null,
                    modifier = Modifier.width(240.dp)
                )
            }
            FadeInItem(delay = 1500L) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Seu novo app de saúde fitness e bons hábitos.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconBg(content = {
                            Text(
                                text = "🙋🏻‍♀️",
                                fontSize = 32.sp
                            )
                        })
                        IconBg(content = {
                            Text(
                                text = "🥦",
                                fontSize = 32.sp
                            )
                        })
                        IconBg(content = {
                            Text(
                                text = "🥤",
                                fontSize = 32.sp
                            )
                        })
                    }
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FadeInItem(delay = 2000L) {
                Image(
                    painter = painterResource(arrowLogo),
                    contentDescription = null,
                    modifier = Modifier.size(142.dp)
                )
            }
        }
    }
}