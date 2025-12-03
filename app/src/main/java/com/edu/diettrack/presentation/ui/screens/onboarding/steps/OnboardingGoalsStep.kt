package com.edu.diettrack.presentation.ui.screens.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.diettrack.presentation.components.AppStepper
import com.edu.diettrack.presentation.components.AppStepperUnit
import com.edu.diettrack.presentation.components.IconBg

@Composable
fun OnboardingGoalsStep(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconBg(
                bgSize = 143.dp,
                content = {
                    Text(
                        text = "✅️",
                        fontSize = 64.sp
                    )
                }
            )
            Text(
                text = "Conte-nos mais sobre seus objetivos.",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            AppStepper(
                label = "Qual a sua altura atual?",
                currentStep = 160,
                onIncrement = {},
                onDecrement = {},
                min = 90,
                max = 300,
                unit = AppStepperUnit.CM,
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Qual o seu peso atual?",
                currentStep = 75,
                onIncrement = {},
                onDecrement = {},
                min = 30,
                max = 500,
                unit = AppStepperUnit.KG,
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Qual o seu peso ideal?",
                currentStep = 85,
                onIncrement = {},
                onDecrement = {},
                min = 30,
                max = 300,
                unit = AppStepperUnit.KG,
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Quanto de água você pretende beber por dia?",
                currentStep = 4000,
                onIncrement = {},
                onDecrement = {},
                min = 0,
                max = 190,
                unit = AppStepperUnit.ML,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}