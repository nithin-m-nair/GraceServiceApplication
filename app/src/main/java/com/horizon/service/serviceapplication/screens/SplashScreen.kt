package com.horizon.service.serviceapplication.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.horizon.service.serviceapplication.R

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Animation for alpha (fade-in)
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Fade-in animation
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500, // 1.5 seconds fade-in
                easing = LinearOutSlowInEasing
            )
        )

        // Keep splash for a short time before moving to main screen
        delay(1000)
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0E171F)), // corrected background color
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.logo_text), // your custom image
            contentDescription = "Splash Image",
            contentScale = ContentScale.None,
            modifier = Modifier
                .fillMaxSize() // adjust size
                .alpha(alpha.value)
        )
    }
}


