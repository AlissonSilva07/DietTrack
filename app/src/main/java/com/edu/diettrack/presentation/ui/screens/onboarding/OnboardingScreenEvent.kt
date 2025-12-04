package com.edu.diettrack.presentation.ui.screens.onboarding

sealed class OnboardingScreenEvent {
    object OnPreviousStep : OnboardingScreenEvent()
    object OnNextStep : OnboardingScreenEvent()
    object OnFinish : OnboardingScreenEvent()
    object OnDismissModal : OnboardingScreenEvent()
    object OnOpenModal : OnboardingScreenEvent()

    //OnboardingPersonalInfo
    data class OnProfilePictureChange(val image: String) : OnboardingScreenEvent()
    data class OnChangeGender(val gender: String) : OnboardingScreenEvent()
    object OnIncrementAge : OnboardingScreenEvent()
    object OnDecrementAge : OnboardingScreenEvent()

    //OnboardingGoals
    object OnIncrementHeight : OnboardingScreenEvent()
    object OnDecrementHeight : OnboardingScreenEvent()
    object OnIncrementCurrentWeight : OnboardingScreenEvent()
    object OnDecrementCurrentWeight : OnboardingScreenEvent()
    object OnIncrementIdealWeight : OnboardingScreenEvent()
    object OnDecrementIdealWeight : OnboardingScreenEvent()
    object OnIncrementWaterGoal : OnboardingScreenEvent()
    object OnDecrementWaterGoal : OnboardingScreenEvent()
}