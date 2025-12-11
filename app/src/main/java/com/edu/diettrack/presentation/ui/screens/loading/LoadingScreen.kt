package com.edu.diettrack.presentation.ui.screens.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.edu.diettrack.presentation.ui.screens.auth.AuthState
import com.edu.diettrack.presentation.ui.screens.auth.AuthViewModel
import com.edu.diettrack.presentation.ui.viewmodels.AppStartViewModel

@Composable
fun LoadingScreen(
    appStartViewModel: AppStartViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel(),
    onNavigateToOnboarding: () -> Unit,
    onNavigateToMain: () -> Unit
) {
    val onboarded by appStartViewModel.hasOnboarded.collectAsState(initial = false)
    val authState by authViewModel.state.collectAsState()

    LaunchedEffect(onboarded, authState) {
        if (onboarded && authState is AuthState.Success) {
            onNavigateToMain()
        } else {
            onNavigateToOnboarding()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Aguarde...",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}