package com.edu.diettrack.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.edu.diettrack.R
import com.edu.diettrack.presentation.ui.theme.AppTheme

enum class AppStepperUnit {
    CM,
    ML,
    KG
}


@Composable
fun AppStepper(
    modifier: Modifier = Modifier,
    label: String,
    currentStep: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    min: Int = 0,
    max: Int = 2,
    unit: AppStepperUnit? = null
) {
    val isDecrementEnabled = currentStep > min
    val isIncrementEnabled = currentStep < max

    val unit = when (unit) {
        AppStepperUnit.CM -> "cm"
        AppStepperUnit.ML -> "ml"
        AppStepperUnit.KG -> "kg"
        else -> null
    }

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
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                AnimatedContent(
                    targetState = currentStep,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInVertically(
                                animationSpec = tween(150)
                            ) { height -> height } togetherWith
                                    slideOutVertically(
                                        animationSpec = tween(150)
                                    ) { height -> -height }
                        } else {
                            slideInVertically(
                                animationSpec = tween(150)
                            ) { height -> -height } togetherWith
                                    slideOutVertically(
                                        animationSpec = tween(150)
                                    ) { height -> height }
                        }
                    },
                    label = "stepper-slide"
                ) { value ->
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                }

                unit?.let {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
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
                max = 20,
                unit = AppStepperUnit.CM
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
                max = 20,
                unit = AppStepperUnit.CM
            )
        }

    }
}