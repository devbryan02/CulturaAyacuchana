package com.amver.cultura_ayacucho

import HomeView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.amver.cultura_ayacucho.ui.theme.Cultura_ayacuchoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo de pantalla completa
        setContent {
            Cultura_ayacuchoTheme {
                HomeView()
                //LoginScreen()
            }
        }
    }
}
