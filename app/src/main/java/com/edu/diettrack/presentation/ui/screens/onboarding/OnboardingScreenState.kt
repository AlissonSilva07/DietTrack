package com.edu.diettrack.presentation.ui.screens.onboarding

import androidx.compose.foundation.text.input.TextFieldState
import com.edu.diettrack.data.mock.AppMocks
import com.edu.diettrack.domain.model.OnboardingStep

data class OnboardingScreenState (
    val currentStepIndex: Int = 0,
    val steps: List<OnboardingStep> = AppMocks.onBoardingSteps,
    val isFinished: Boolean = false,
    val isModalOpen: Boolean = false,

    //OnboardingPersonalInfo
    val profilePicture: String? = null,
    val name: TextFieldState = TextFieldState(),
    val nameError: String? = null,
    val genderList: List<String> = listOf("Masculino", "Feminino", "Outro"),
    val gender: String? = null,
    val genderError: String? = null,
    val age: Int = 18,
)