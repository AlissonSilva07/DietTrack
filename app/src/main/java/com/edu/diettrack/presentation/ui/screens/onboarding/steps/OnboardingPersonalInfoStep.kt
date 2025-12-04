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
import com.edu.diettrack.presentation.components.AppSelector
import com.edu.diettrack.presentation.components.AppStepper
import com.edu.diettrack.presentation.components.AppTextField
import com.edu.diettrack.presentation.components.AvatarField
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreenEvent
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreenState
import com.edu.diettrack.presentation.ui.theme.AppTheme

@Composable
fun OnboardingPersonalInfoStep(
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
            AvatarField(
                base64Image = state.profilePicture,
                onImagePicked = {
                    event(OnboardingScreenEvent.OnImagePicked(it))
                }
            )
            Text(
                text = "Conte-nos mais sobre você.",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            AppTextField(
                label = "Qual o seu nome?",
                state = state.name,
                placeholder = "Seu nome de preferência",
                icon = null,
                errorText = null,
                modifier = Modifier.fillMaxWidth()
            )
            AppSelector(
                label = "Qual o seu gênero?",
                selectedValue = state.gender,
                error = null,
                onClick = {
                    event(OnboardingScreenEvent.OnOpenModal)
                },
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Qual a sua idade?",
                currentStep = state.age,
                onIncrement = {
                    event(OnboardingScreenEvent.OnIncrementAge)
                },
                onDecrement = {
                    event(OnboardingScreenEvent.OnDecrementAge)
                },
                min = 0,
                max = 190,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingPersonalInfoStepPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OnboardingPersonalInfoStep(
            state = OnboardingScreenState(),
            event = {}
        )
    }
}

@Preview
@Composable
private fun OnboardingPersonalInfoStepPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        OnboardingPersonalInfoStep(
            state = OnboardingScreenState(),
            event = {}
        )
    }
}