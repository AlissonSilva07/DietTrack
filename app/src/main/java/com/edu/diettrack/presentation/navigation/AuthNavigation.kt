package com.edu.diettrack.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.edu.diettrack.presentation.components.AuthScaffold
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginScreen
import com.edu.diettrack.presentation.ui.screens.auth.signin.SignInScreen

@Composable
fun AuthNavigation(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val authBackStack = rememberNavBackStack(
        AppDestinations.Auth.Login
    )
    NavDisplay(
        backStack = authBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppDestinations.Auth.Login> {
                AuthScaffold { modifier, snackbarHostState ->
                    LoginScreen(
                        modifier = modifier,
                        onLogin = onLogin,
                        onNavigateToSignUp = {
                            authBackStack.add(AppDestinations.Auth.Register)
                        },
                        snackbarHostState = snackbarHostState

                    )
                }
            }
            entry<AppDestinations.Auth.Register> {
                AuthScaffold { modifier, snackbarHostState ->
                    SignInScreen(
                        modifier = modifier,
                        onNavigateToLogin = {
                            authBackStack.removeLastOrNull()
                        },
                        snackbarHostState = snackbarHostState
                    )
                }
            }
        }
    )
}