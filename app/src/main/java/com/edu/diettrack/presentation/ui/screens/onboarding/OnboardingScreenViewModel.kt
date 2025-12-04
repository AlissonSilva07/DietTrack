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
                _state.update {
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
            is OnboardingScreenEvent.OnProfilePictureChange -> _state.update { it.copy(profilePicture = event.image) }
            OnboardingScreenEvent.OnDecrementCurrentWeight -> _state.update {
                it.copy(currentWeight = decrement(it.currentWeight))
            }

            OnboardingScreenEvent.OnDecrementHeight -> _state.update {
                it.copy(height = decrement(it.height))
            }

            OnboardingScreenEvent.OnDecrementIdealWeight -> _state.update {
                it.copy(idealWeight = decrement(it.idealWeight))
            }

            OnboardingScreenEvent.OnDecrementWaterGoal -> _state.update {
                it.copy(waterGoal = decrement(it.waterGoal))
            }

            OnboardingScreenEvent.OnIncrementCurrentWeight -> _state.update {
                it.copy(currentWeight = increment(it.currentWeight, 500))
            }

            OnboardingScreenEvent.OnIncrementHeight -> _state.update {
                it.copy(height = increment(it.height, 300))
            }

            OnboardingScreenEvent.OnIncrementIdealWeight -> _state.update {
                it.copy(idealWeight = increment(it.idealWeight, 500))
            }

            OnboardingScreenEvent.OnIncrementWaterGoal -> _state.update {
                it.copy(waterGoal = increment(it.waterGoal, 8000))
            }
        }
    }

    fun increment(value: Int, max: Int): Int =
        if (value < max) value + 1 else value

    fun decrement(value: Int, min: Int = 0): Int =
        if (value > min) value - 1 else value

}
