package com.katesune.filmograf.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.katesune.filmograf.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_font)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),

    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_font)),
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.10.sp
    ),

    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_font)),
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.15.sp
    ),
     /* Other default text styles to override
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val Typography.quote: TextStyle
    get() = TextStyle(
        fontFamily = FontFamily(Font(R.font.new_font)),
        fontWeight = FontWeight.ExtraLight,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )


