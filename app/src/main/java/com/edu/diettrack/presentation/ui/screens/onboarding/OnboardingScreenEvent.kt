package com.edu.diettrack.presentation.ui.screens.onboarding

sealed class OnboardingScreenEvent {
    object OnPreviousStep : OnboardingScreenEvent()
    object OnNextStep : OnboardingScreenEvent()
    object OnFinish : OnboardingScreenEvent()
}