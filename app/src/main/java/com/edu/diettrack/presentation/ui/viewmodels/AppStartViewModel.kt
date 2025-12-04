package com.edu.diettrack.presentation.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.edu.diettrack.data.storage.UserStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AppStartViewModel @Inject constructor(
    private val userStorage: UserStorage
) : ViewModel() {

    val hasOnboarded: Flow<Boolean> = userStorage.onboarded
}
