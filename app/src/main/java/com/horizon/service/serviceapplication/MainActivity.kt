package com.horizon.service.serviceapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.horizon.service.serviceapplication.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // This is the key. You can use this to keep the splash screen on
        // for longer, for example, while data is loading.
        // For now, let's keep it simple.

        setContent {
          //  var showSplash by remember { mutableStateOf(true) }

            MaterialTheme { // Use your app's main theme here
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                  //  if (showSplash) {
                        // Animated Splash
//                        SplashScreen {
//                            showSplash = false
                  //      }
                 //   } else {
                        // Your main navigation
                        AppNavigation()
                   // }
                }
            }
        }
    }
}
