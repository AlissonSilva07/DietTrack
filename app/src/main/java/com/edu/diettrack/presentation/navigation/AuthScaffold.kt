package com.edu.diettrack.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun AuthScaffold(
    content: @Composable (
        modifier: Modifier,
        snackbarHostState: SnackbarHostState
    ) -> Unit
) {
    val containerColor = MaterialTheme.colorScheme.background
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        containerColor = containerColor,
        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Snackbar(
                    snackbarData,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.background,
                    actionContentColor = MaterialTheme.colorScheme.background,
                    actionColor = MaterialTheme.colorScheme.background
                )
            }
        ) }
    ) { innerPadding ->
        content(
            Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
            snackbarHostState
        )
    }
}
