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
import com.edu.diettrack.presentation.ui.screens.AuthState
import com.edu.diettrack.presentation.ui.screens.AuthViewModel
import com.edu.diettrack.presentation.ui.screens.HomeScreen
import com.edu.diettrack.presentation.ui.screens.LoginScreen
import com.edu.diettrack.presentation.ui.screens.SignInScreen

@Composable
fun AppNavigation(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state = authViewModel.state.collectAsState()

    val start = when (state.value) {
        is AuthState.Loading -> Loading
        is AuthState.Unauthenticated -> Login
        is AuthState.Success -> Home
        else -> {}
    }

    NavHost(
        navController = navController,
        startDestination = AuthRoutes
    ) {

        authGraph(
            navController = navController,
            onAuthenticated = {}
        )

        mainGraph(
            onLogout = {
                navController.navigate(AuthRoutes) {
                    popUpTo(MainRoutes) { inclusive = true }
                }
            }
        )
    }
}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onAuthenticated: () -> Unit,
) {
    navigation<AuthRoutes>(startDestination = Login) {
        composable<Login> {
            AuthScaffold { modifier ->
                LoginScreen(
                    modifier = modifier,
                    onNavigateToSignUp = {
                        navController.navigate(Signin)
                    }
                )
            }
        }
        composable<Signin> {
            AuthScaffold { modifier ->
                SignInScreen(
                    modifier = modifier,
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.mainGraph(
    onLogout: () -> Unit,
) {
    navigation<MainRoutes>(startDestination = Home) {
        composable<Home> {
            HomeScreen()
        }
    }
}
