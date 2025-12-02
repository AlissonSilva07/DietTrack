package com.edu.diettrack.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.diettrack.presentation.ui.theme.AppTheme

enum class ButtonVariant {
    DEFAULT,
    DISABLED,
    MUTED,
    DESTRUCTIVE
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    title: String,
    variant: ButtonVariant,
    disabled: Boolean,
    onClick: () -> Unit,
    icon: @Composable (() -> Unit)? = null,
    loading: Boolean = false
) {

    val backgroundColor = when (variant) {
        ButtonVariant.DEFAULT -> MaterialTheme.colorScheme.primary
        ButtonVariant.DISABLED -> MaterialTheme.colorScheme.surface
        ButtonVariant.MUTED -> MaterialTheme.colorScheme.background
        ButtonVariant.DESTRUCTIVE -> MaterialTheme.colorScheme.error
    }

    val contentColor = when (variant) {
        ButtonVariant.DEFAULT -> MaterialTheme.colorScheme.onPrimary
        ButtonVariant.DISABLED -> Color.White
        ButtonVariant.MUTED -> MaterialTheme.colorScheme.primary
        ButtonVariant.DESTRUCTIVE -> Color.White
    }

    val border = when (variant) {
        ButtonVariant.MUTED -> BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        else -> null
    }

    Button(
        onClick = {
            if (!disabled) {
                onClick()
            }
        },
        colors = ButtonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = MaterialTheme.colorScheme.onBackground,
            disabledContentColor = MaterialTheme.colorScheme.background,
        ),
        border = border,
        enabled = variant != ButtonVariant.DISABLED,
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        modifier = modifier
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                color = MaterialTheme.colorScheme.background,
                strokeWidth = 2.dp
            )
        } else {
            if (icon != null) {
                icon()
            }
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.DEFAULT,
                disabled = false,
                loading = true,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.DISABLED,
                disabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.DESTRUCTIVE,
                disabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.MUTED,
                disabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun ButtonPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.DEFAULT,
                disabled = false,
                loading = true,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.DISABLED,
                disabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.DESTRUCTIVE,
                disabled = false,
                modifier = Modifier.fillMaxWidth()
            )
            AppButton(
                onClick = {},
                title = "Entrar",
                variant = ButtonVariant.MUTED,
                disabled = false,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

