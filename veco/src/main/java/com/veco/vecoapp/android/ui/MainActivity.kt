package com.veco.vecoapp.android.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        testMaterialText = assets.open("material_text.mat")
            .bufferedReader()
            .use(BufferedReader::readText)

        setContent {
            VecoApp()
        }
    }
}

lateinit var testMaterialText: String // TEMP
