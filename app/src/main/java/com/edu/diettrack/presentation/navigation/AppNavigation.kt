package com.edu.diettrack.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.edu.diettrack.presentation.components.OnboardingScaffold
import com.edu.diettrack.presentation.ui.screens.loading.LoadingScreen
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreen

@Composable
fun AppNavigation() {
    val rootBackStack = rememberNavBackStack(AppDestinations.Loading)

    NavDisplay(
        backStack = rootBackStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppDestinations.Loading> {
                LoadingScreen(
                    onNavigateToOnboarding = {
                        rootBackStack.add(AppDestinations.Onboarding)
                    },
                    onNavigateToMain = {
                        rootBackStack.add(AppDestinations.Main.Home)
                    }
                )
            }
            entry<AppDestinations.Onboarding> {
                OnboardingScaffold { modifier ->
                    OnboardingScreen(
                        modifier = modifier
                    )
                }
            }
            entry<AppDestinations.Auth> {
                AuthNavigation(
                    onLogin = {
                        rootBackStack.remove(AppDestinations.Auth)
                        rootBackStack.add(AppDestinations.Main)
                    }
                )
            }
            entry<AppDestinations.Main> {
                MainNavigation(
                    onLogout = {
                        rootBackStack.remove(AppDestinations.Main)
                        rootBackStack.add(AppDestinations.Auth)
                    }
                )
            }
        }
    )
}

