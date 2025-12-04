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
            OnboardingScreenEvent.OnFinish -> _state.update { it.copy(isFinished = true) }
            OnboardingScreenEvent.OnNextStep -> {
                _state.update{
                    val newIndex = increment(it.currentStepIndex, it.steps.size - 1)
                    it.copy(currentStepIndex = newIndex)
                }
            }
            OnboardingScreenEvent.OnPreviousStep -> {
                _state.update {
                    it.copy(currentStepIndex = decrement(it.currentStepIndex))
                }
            }
            is OnboardingScreenEvent.OnChangeGender -> _state.update { it.copy(gender = event.gender) }
            OnboardingScreenEvent.OnDecrementAge -> {
                _state.update {
                    it.copy(age = decrement(it.age))
                }
            }
            OnboardingScreenEvent.OnIncrementAge -> {
                _state.update {
                    it.copy(age = increment(it.age, 130))
                }
            }
            OnboardingScreenEvent.OnDismissModal -> _state.update { it.copy(isModalOpen = false) }
            OnboardingScreenEvent.OnOpenModal -> _state.update { it.copy(isModalOpen = true) }
            is OnboardingScreenEvent.OnImagePicked -> _state.update { it.copy(profilePicture = event.image) }
        }
    }

    fun increment(value: Int, max: Int): Int =
        if (value < max) value + 1 else value

    fun decrement(value: Int, min: Int = 0): Int =
        if (value > min) value - 1 else value

}
