package com.edu.diettrack.presentation.ui.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.edu.diettrack.R
import com.edu.diettrack.presentation.components.AppButton
import com.edu.diettrack.presentation.components.ButtonVariant
import com.edu.diettrack.presentation.components.PagerIndicator
import com.edu.diettrack.presentation.navigation.OnboardingScaffold
import com.edu.diettrack.presentation.ui.screens.onboarding.steps.OnboardingFinalStep
import com.edu.diettrack.presentation.ui.screens.onboarding.steps.OnboardingGoalsStep
import com.edu.diettrack.presentation.ui.screens.onboarding.steps.OnboardingPersonalInfoStep
import com.edu.diettrack.presentation.ui.screens.onboarding.steps.OnboardingWelcomeStep
import com.edu.diettrack.presentation.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    OnBoarding(
        modifier = modifier,
        state = state,
        event = viewModel::onEvent
    )
}

@Composable
fun OnBoarding(
    modifier: Modifier = Modifier,
    state: OnboardingScreenState,
    event: (OnboardingScreenEvent) -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val multipliedScreenWidth = screenWidth * 4

    val pagerState = rememberPagerState(
        initialPage = state.currentStepIndex,
        pageCount = { state.steps.size }
    )

    val bottomButtonLabel = when (pagerState.currentPage) {
        0 -> "Começar"
        1 -> "Próximo"
        2 -> "Próximo"
        else -> "Finalizar"
    }

    val shouldShowVoltarButton = pagerState.currentPage != 0

    val shouldShowStripe = if (pagerState.currentPage == state.steps.size - 1) {
        true
    } else {
        false
    }

    LaunchedEffect(state.currentStepIndex) {
        pagerState.animateScrollToPage(state.currentStepIndex)
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {},
                shape = RoundedCornerShape(percent = 100),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Pular",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            when (page) {
                0 -> OnboardingWelcomeStep()
                1 -> OnboardingPersonalInfoStep()
                2 -> OnboardingGoalsStep()
                3 -> OnboardingFinalStep()
            }
        }
        AnimatedVisibility(
            visible = shouldShowStripe
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(unbounded = true),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.stripe),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .width(multipliedScreenWidth)
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PagerIndicator(
                pagerState = pagerState,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (shouldShowVoltarButton) {
                    AppButton(
                        title = "Anterior",
                        variant = ButtonVariant.MUTED,
                        disabled = false,
                        onClick = {
                            event(OnboardingScreenEvent.OnPreviousStep)
                        },
                        icon = null
                    )
                }
                AppButton(
                    title = bottomButtonLabel,
                    variant = ButtonVariant.DEFAULT,
                    disabled = false,
                    onClick = {
                        event(OnboardingScreenEvent.OnNextStep)
                    },
                    icon = null
                )
            }
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    AppTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        OnboardingScaffold { modifier ->
            OnboardingScreen(modifier)
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreviewDark() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        OnboardingScaffold { modifier ->
            OnboardingScreen(modifier)
        }
    }
}