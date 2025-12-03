package com.edu.diettrack.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.ui.theme.AppTheme

@Composable
fun AppStepper(
    modifier: Modifier = Modifier,
    label: String,
    currentStep: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    min: Int = 0,
    max: Int = 2
) {
    val isDecrementEnabled = currentStep > min
    val isIncrementEnabled = currentStep < max

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .padding(0.dp)
                .clip(RoundedCornerShape(percent = 100))
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    if (isDecrementEnabled) onDecrement()
                },
                shape = RoundedCornerShape(percent = 100),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.remove_24px),
                    contentDescription = null
                )
            }
            Text(
                text = "$currentStep",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    if (isIncrementEnabled) onIncrement()
                },
                shape = RoundedCornerShape(percent = 100),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppStepperPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            AppStepper(
                label = "Qual a sua altura?",
                currentStep = 2,
                onIncrement = {},
                onDecrement = {},
                min = 0,
                max = 20
            )
        }
        
    }
}

@Preview
@Composable
private fun AppStepperPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Column(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            AppStepper(
                label = "Qual a sua altura?",
                currentStep = 2,
                onIncrement = {},
                onDecrement = {},
                min = 0,
                max = 20
            )
        }

    }
}