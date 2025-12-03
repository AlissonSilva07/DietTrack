package com.edu.diettrack.presentation.ui.screens.onboarding

import com.edu.diettrack.data.mock.AppMocks
import com.edu.diettrack.domain.model.OnboardingStep

data class OnboardingScreenState (
    val currentStepIndex: Int = 0,
    val steps: List<OnboardingStep> = AppMocks.onBoardingSteps,
    val isFinished: Boolean = false
)