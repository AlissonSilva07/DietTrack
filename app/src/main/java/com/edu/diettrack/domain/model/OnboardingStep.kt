package com.edu.diettrack.domain.model

import androidx.compose.runtime.Composable

enum class OnboardingScreen {
    WELCOME,
    PERSONAL_INFO,
    GOALS,
    FINAL
}

data class OnboardingStep (
    val index: Int,
    val screen: OnboardingScreen
)