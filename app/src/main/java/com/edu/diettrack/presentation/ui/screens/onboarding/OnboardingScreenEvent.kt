package com.edu.diettrack.presentation.ui.screens.onboarding

sealed class OnboardingScreenEvent {
    object OnPreviousStep : OnboardingScreenEvent()
    object OnNextStep : OnboardingScreenEvent()
    object OnFinish : OnboardingScreenEvent()
    object OnDismissModal : OnboardingScreenEvent()
    object OnOpenModal : OnboardingScreenEvent()

    //OnboardingPersonalInfo
    data class OnImagePicked(val image: String) : OnboardingScreenEvent()
    data class OnChangeGender(val gender: String) : OnboardingScreenEvent()
    object OnIncrementAge : OnboardingScreenEvent()
    object OnDecrementAge : OnboardingScreenEvent()
}