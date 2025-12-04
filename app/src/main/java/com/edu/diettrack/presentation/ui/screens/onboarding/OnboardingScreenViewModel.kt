package com.edu.diettrack.presentation.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OnboardingScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(OnboardingScreenState())
    val state: StateFlow<OnboardingScreenState> = _state.asStateFlow()

    fun onEvent(event: OnboardingScreenEvent) {
        when (event) {
            OnboardingScreenEvent.OnFinish ->
                _state.update { it.copy(isFinished = true) }

            OnboardingScreenEvent.OnNextStep -> {
                if (state.value.currentStepIndex == 1 && !validateNameAndGender()) return

                _state.update {
                    val next = increment(it.currentStepIndex, it.steps.size - 1)
                    it.copy(currentStepIndex = next)
                }
            }

            OnboardingScreenEvent.OnPreviousStep ->
                _state.update { it.copy(currentStepIndex = decrement(it.currentStepIndex)) }

            is OnboardingScreenEvent.OnChangeGender ->
                _state.update { it.copy(gender = event.gender) }

            OnboardingScreenEvent.OnDecrementAge ->
                _state.update { it.copy(age = decrement(it.age)) }

            OnboardingScreenEvent.OnIncrementAge ->
                _state.update { it.copy(age = increment(it.age, 130)) }

            OnboardingScreenEvent.OnDismissModal ->
                _state.update { it.copy(isModalOpen = false) }

            OnboardingScreenEvent.OnOpenModal ->
                _state.update { it.copy(isModalOpen = true) }

            is OnboardingScreenEvent.OnProfilePictureChange ->
                _state.update { it.copy(profilePicture = event.image) }

            OnboardingScreenEvent.OnDecrementCurrentWeight ->
                _state.update { it.copy(currentWeight = decrement(it.currentWeight)) }

            OnboardingScreenEvent.OnDecrementHeight ->
                _state.update { it.copy(height = decrement(it.height)) }

            OnboardingScreenEvent.OnDecrementIdealWeight ->
                _state.update { it.copy(idealWeight = decrement(it.idealWeight)) }

            OnboardingScreenEvent.OnDecrementWaterGoal ->
                _state.update { it.copy(waterGoal = decrementByStep(it.waterGoal, 0)) }

            OnboardingScreenEvent.OnIncrementCurrentWeight ->
                _state.update { it.copy(currentWeight = increment(it.currentWeight, 500)) }

            OnboardingScreenEvent.OnIncrementHeight ->
                _state.update { it.copy(height = increment(it.height, 300)) }

            OnboardingScreenEvent.OnIncrementIdealWeight ->
                _state.update { it.copy(idealWeight = increment(it.idealWeight, 500)) }

            OnboardingScreenEvent.OnIncrementWaterGoal ->
                _state.update { it.copy(waterGoal = incrementByStep(it.waterGoal, 8000)) }
        }
    }

    private fun validateNameAndGender(): Boolean {
        val current = _state.value
        var isValid = true

        if (current.name.text.isBlank()) {
            _state.update { it.copy(nameError = "Campo obrigatório.") }
            isValid = false
        } else if (current.name.text.length < 3) {
            _state.update { it.copy(nameError = "Mínimo de 3 caracteres.") }
            isValid = false
        } else {
            _state.update { it.copy(nameError = null) }
        }

        if (current.gender == null) {
            _state.update { it.copy(genderError = "Campo obrigatório.") }
            isValid = false
        } else {
            _state.update { it.copy(genderError = null) }
        }

        return isValid
    }

    fun increment(value: Int, max: Int): Int =
        if (value < max) value + 1 else value

    fun decrement(value: Int, min: Int = 0): Int =
        if (value > min) value - 1 else value

    fun incrementByStep(value: Int, max: Int, step: Int = 500): Int =
        if (value < max) value + step else value

    fun decrementByStep(value: Int, min: Int, step: Int = 500): Int =
        if (value > min) value - step else value
}

