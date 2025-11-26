@file:OptIn(ExperimentalMaterial3Api::class)

package com.edu.diettrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.edu.diettrack.presentation.navigation.AuthScaffold
import com.edu.diettrack.presentation.ui.screens.LoginScreen
import com.edu.diettrack.presentation.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                AuthScaffold {
                    LoginScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTheme(
        dynamicColor = false,
        darkTheme = false
    ) {
        LoginScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreviewDark() {
    AppTheme(
        dynamicColor = false,
        darkTheme = true
    ) {
        LoginScreen()
    }
}