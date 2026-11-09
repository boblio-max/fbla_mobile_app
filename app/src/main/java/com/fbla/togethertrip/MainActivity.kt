package com.fbla.togethertrip

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.fbla.togethertrip.ui.navigation.AppNav
import com.fbla.togethertrip.ui.theme.TogetherTripTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TogetherTripTheme {
                Surface { AppNav(rememberNavController()) }
            }
        }
    }
}