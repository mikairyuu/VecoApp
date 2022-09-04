package com.veco.vecoapp.android.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.di.di
import com.veco.vecoapp.storage.KeyDefaults
import com.veco.vecoapp.storage.KeyValueStorage
import org.kodein.di.direct
import org.kodein.di.instance
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        testMaterialText = assets.open("material_text.mat")
            .bufferedReader()
            .use(BufferedReader::readText)

        var startScreen: String = Screen.Home.route
        val keyValueStorage = di.direct.instance<KeyValueStorage>()
        if (!keyValueStorage.contains(KeyDefaults.KEY_USER_TOKEN, true)) {
            startScreen = Screen.Auth.route
        }

        setContent {
            VecoApp(startScreen)
        }
    }
}

lateinit var testMaterialText: String // TEMP
