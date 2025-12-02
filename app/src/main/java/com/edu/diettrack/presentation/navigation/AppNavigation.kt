package com.edu.diettrack.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.edu.diettrack.presentation.ui.screens.auth.AuthState
import com.edu.diettrack.presentation.ui.screens.auth.AuthViewModel
import com.edu.diettrack.presentation.ui.screens.auth.login.LoginScreen
import com.edu.diettrack.presentation.ui.screens.auth.signin.SignInScreen
import com.edu.diettrack.presentation.ui.screens.home.HomeScreen
import com.edu.diettrack.presentation.ui.screens.onboarding.OnboardingScreen

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val authState = authViewModel.state.collectAsState()

    val startDestination = when (authState.value) {
        is AuthState.Loading -> AuthRoutes
        is AuthState.Unauthenticated -> AuthRoutes
        is AuthState.Success -> MainRoutes
        is AuthState.Error -> {}
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        authGraph(
            navController = navController,
            onAuthenticated = {
                navController.navigate(MainRoutes) {
                    popUpTo(AuthRoutes) { inclusive = true }
                }
            }
        )

        mainGraph(
            onLogout = {
                authViewModel.logout()

                navController.navigate(AuthRoutes) {
                    popUpTo(MainRoutes) { inclusive = true }
                }
            }
        )
    }
}


fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onAuthenticated: () -> Unit
) {
    navigation<AuthRoutes>(startDestination = Login) {

        composable<Login> {
            AuthScaffold { modifier, snackbarHostState ->
                LoginScreen(
                    modifier = modifier,
                    onNavigateToSignUp = {
                        navController.navigate(Signin)
                    },
                    onLoginSuccess = {
                        onAuthenticated()
                    },
                    snackbarHostState = snackbarHostState
                )
            }
        }

        composable<Signin> {
            AuthScaffold { modifier, snackbarHostState ->
                SignInScreen(
                    modifier = modifier,
                    onNavigateToLogin = { navController.popBackStack() },
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}


fun NavGraphBuilder.mainGraph(
    onLogout: () -> Unit
) {
    navigation<MainRoutes>(startDestination = Home) {

        composable<Home> {
            MainScaffold { modifier ->
                HomeScreen(
                    modifier = modifier,
                    onLogout = onLogout
                )
            }
        }

        composable<Onboarding> {
            OnboardingScaffold { modifier ->
                OnboardingScreen(
                    modifier = modifier,
                )
            }
        }
    }
}
