package com.edu.diettrack.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppDestinations : NavKey {
    @Serializable
    data object Loading : AppDestinations, NavKey

    @Serializable
    data object Onboarding : AppDestinations, NavKey

    @Serializable
    data object Auth : AppDestinations, NavKey {
        @Serializable
        data object Login : AppDestinations, NavKey
        @Serializable
        data object Register : AppDestinations, NavKey
    }

    @Serializable
    data object Main : AppDestinations, NavKey {
        @Serializable
        data object Home : AppDestinations, NavKey
    }
}
