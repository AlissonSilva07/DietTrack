package com.edu.diettrack.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.edu.diettrack.R

val Inter = FontFamily(
    Font(R.font.inter_variable)
)

val MomoTrust = FontFamily(
    Font(R.font.momo_regular)
)

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = MomoTrust,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleSmall = TextStyle(
        fontFamily = MomoTrust,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Inter,
        fontSize = 12.sp
    ),
)

