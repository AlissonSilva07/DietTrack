package com.edu.diettrack.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.diettrack.presentation.ui.theme.AppTheme

@Composable
fun AppModalPicker(
    modifier: Modifier = Modifier,
    label: String,
    onDismiss: () -> Unit,
    options: List<String>?,
    selectedOption: String? = null,
    onOptionSelected: (String) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        if (options.isNullOrEmpty()) {
            Text(
                text = "Nenhuma opção disponível.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = options) { option ->
                    val isSelectedOption = option == selectedOption

                    val backgroundColor by animateColorAsState(
                        targetValue = if (isSelectedOption) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.background,
                        label = "picker-bg"
                    )

                    val contentColor by animateColorAsState(
                        targetValue = if (isSelectedOption) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onBackground,
                        label = "picker-fg"
                    )

                    val scale by animateFloatAsState(
                        targetValue = if (isSelectedOption) 1.03f else 1f,
                        label = "picker-scale"
                    )

                    Card(
                        modifier = Modifier.graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = backgroundColor,
                            contentColor = contentColor
                        ),
                        shape = RoundedCornerShape(8.dp),
                        onClick = { onOptionSelected(option) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelectedOption,
                                onClick = { onOptionSelected(option) },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.onPrimary,
                                    unselectedColor = MaterialTheme.colorScheme.onBackground
                                )
                            )
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = if (isSelectedOption) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
        AppButton(
            title = "Fechar",
            variant = ButtonVariant.MUTED,
            disabled = false,
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun AppModalPickerPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        AppModalPicker(
            label = "Escolha um gênero",
            onDismiss = {},
            options = listOf("Opção 1", "Opção 2", "Opção 3"),
            selectedOption = "Opção 1",
            onOptionSelected = {}
        )
    }
}

@Preview
@Composable
private fun AppModalPickerPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        AppModalPicker(
            label = "Escolha um gênero",
            onDismiss = {},
            options = listOf("Opção 1", "Opção 2", "Opção 3"),
            selectedOption = "Opção 1",
            onOptionSelected = {}
        )
    }
}