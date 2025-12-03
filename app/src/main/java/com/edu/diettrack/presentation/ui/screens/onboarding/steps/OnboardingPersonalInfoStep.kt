package com.edu.diettrack.presentation.ui.screens.onboarding.steps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edu.diettrack.presentation.components.AppSelector
import com.edu.diettrack.presentation.components.AppStepper
import com.edu.diettrack.presentation.components.AppTextField
import com.edu.diettrack.presentation.components.AvatarField

@Composable
fun OnboardingPersonalInfoStep(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AvatarField()
            Text(
                text = "Conte-nos mais sobre você.",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            AppTextField(
                label = "Qual o seu nome?",
                state = TextFieldState(),
                placeholder = "Seu nome de preferência",
                icon = null,
                errorText = null,
                modifier = Modifier.fillMaxWidth()
            )
            AppSelector(
                label = "Qual o seu gênero?",
                selectedValue = "Masculino",
                error = null,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
            AppStepper(
                label = "Qual a sua idade?",
                currentStep = 2,
                onIncrement = {},
                onDecrement = {},
                min = 0,
                max = 190,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}