package com.edu.diettrack.data.mock

import com.edu.diettrack.domain.model.OnboardingScreen
import com.edu.diettrack.domain.model.OnboardingStep

object AppMocks {

    val onBoardingSteps: List<OnboardingStep> = listOf(
        OnboardingStep(
            index = 0,
            screen = OnboardingScreen.WELCOME
        ),
        OnboardingStep(
            index = 1,
            screen = OnboardingScreen.PERSONAL_INFO
        ),
        OnboardingStep(
            index = 2,
            screen = OnboardingScreen.GOALS
        ),
        OnboardingStep(
            index = 3,
            screen = OnboardingScreen.FINAL
        )
    )
}