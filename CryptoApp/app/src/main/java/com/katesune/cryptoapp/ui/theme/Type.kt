package com.katesune.cryptoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.katesune.cryptoapp.R

private val robotoRegular = FontFamily(Font(R.font.roboto_regular))
private val robotoMedium = FontFamily(Font(R.font.roboto_medium))

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = robotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    ),

    bodyMedium = TextStyle(
        fontFamily = robotoRegular,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        color = Color.Black
    ),

    labelSmall = TextStyle(
        fontFamily = robotoMedium,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    ),

    titleLarge = TextStyle(
        fontFamily = robotoMedium,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.15.sp,
        color = Color.Black
    ),

    titleMedium = TextStyle(
        fontFamily = robotoRegular,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    ),

    titleSmall = TextStyle(
        fontFamily = robotoMedium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.75.sp,
        color = Color.Black
    ),
)

