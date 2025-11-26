package com.edu.diettrack.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.edu.diettrack.presentation.ui.screens.LoginScreen

@Composable
fun AuthScaffold() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LoginScreen(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        )
    }
}