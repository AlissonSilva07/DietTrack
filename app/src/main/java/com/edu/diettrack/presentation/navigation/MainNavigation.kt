package com.edu.diettrack.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.edu.diettrack.presentation.components.AuthScaffold
import com.edu.diettrack.presentation.ui.screens.home.HomeScreen

@Composable
fun MainNavigation(
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mainBackStack = rememberNavBackStack(
        AppDestinations.Main.Home
    )
    NavDisplay(
        backStack = mainBackStack,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<AppDestinations.Main.Home> {
                AuthScaffold { modifier, snackbarHostState ->
                    HomeScreen(
                        modifier = modifier,
                        onLogout = onLogout,
                    )
                }
            }
        }
    )
}