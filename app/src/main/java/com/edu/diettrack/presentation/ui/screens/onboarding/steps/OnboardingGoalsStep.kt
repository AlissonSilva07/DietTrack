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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edu.diettrack.presentation.components.AppStepper
import com.edu.diettrack.presentation.components.AppStepperUnit
import com.edu.diettrack.presentation.components.IconBg
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreenEvent
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreenState
import com.edu.diettrack.presentation.ui.theme.AppTheme

@Composable
fun OnboardingGoalsStep(
    modifier: Modifier = Modifier,
    state: OnboardingScreenState,
    event: (OnboardingScreenEvent) -> Unit
) {
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
                currentStep = state.height,
                onIncrement = {
                    event(OnboardingScreenEvent.OnIncrementHeight)
                },
                onDecrement = {
                    event(OnboardingScreenEvent.OnDecrementHeight)
                },
                min = 90,
                max = 300,
                unit = AppStepperUnit.CM,
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Qual o seu peso atual?",
                currentStep = state.currentWeight,
                onIncrement = {
                    event(OnboardingScreenEvent.OnIncrementCurrentWeight)
                },
                onDecrement = {
                    event(OnboardingScreenEvent.OnDecrementCurrentWeight)
                },
                min = 0,
                max = 500,
                unit = AppStepperUnit.KG,
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Qual o seu peso ideal?",
                currentStep = state.idealWeight,
                onIncrement = {
                    event(OnboardingScreenEvent.OnIncrementIdealWeight)
                },
                onDecrement = {
                    event(OnboardingScreenEvent.OnDecrementIdealWeight)
                },
                min = 0,
                max = 300,
                unit = AppStepperUnit.KG,
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Quanto de água você pretende beber por dia?",
                currentStep = state.waterGoal,
                onIncrement = {
                    event(OnboardingScreenEvent.OnIncrementWaterGoal)
                },
                onDecrement = {
                    event(OnboardingScreenEvent.OnDecrementWaterGoal)
                },
                min = 0,
                max = 8000,
                unit = AppStepperUnit.ML,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingGoalsStepPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OnboardingGoalsStep(
            state = OnboardingScreenState(),
            event = {}
        )
    }
}

@Preview
@Composable
private fun OnboardingGoalsStepPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        OnboardingGoalsStep(
            state = OnboardingScreenState(),
            event = {}
        )
    }
}