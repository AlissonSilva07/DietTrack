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
            OnboardingScreenEvent.OnNextStep -> increaseStep()
            OnboardingScreenEvent.OnPreviousStep -> decreaseStep()
        }
    }

    private fun increaseStep() {
        val current = _state.value.currentStepIndex
        val lastIndex = _state.value.steps.size - 1

        if (current >= lastIndex) return

        _state.update { it.copy(currentStepIndex = current + 1) }
    }

    private fun decreaseStep() {
        val current = _state.value.currentStepIndex

        if (current == 0) return

        _state.update { it.copy(currentStepIndex = current - 1) }
    }
}
