package com.edu.diettrack.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun OnboardingScaffold(content: @Composable (modifier: Modifier) -> Unit) {
    val containerColor = MaterialTheme.colorScheme.background
    Scaffold(
        containerColor = containerColor,
    ) { innerPadding ->
        content(
            Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        )
    }
}
