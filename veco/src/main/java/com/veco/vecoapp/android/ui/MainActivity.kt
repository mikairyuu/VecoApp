package com.veco.vecoapp.android.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.veco.vecoapp.android.ui.navigation.Screen
import com.veco.vecoapp.data.PersistentDataManager
import com.veco.vecoapp.di.di
import com.veco.vecoapp.storage.KeyDefaults
import com.veco.vecoapp.storage.KeyValueStorage
import com.yandex.mapkit.MapKitFactory
import org.kodein.di.direct
import org.kodein.di.instance
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        testMaterialText = assets.open("material_text.mat")
            .bufferedReader()
            .use(BufferedReader::readText)

        var startScreen: String = Screen.Home.route
        val keyValueStorage = di.direct.instance<KeyValueStorage>()
        PersistentDataManager.updateLogin(
            !keyValueStorage.getString(
                KeyDefaults.KEY_USER_TOKEN,
                true
            ).isNullOrBlank()
        )
        if (!PersistentDataManager.login.value) {
            startScreen = Screen.Auth.route
        }

        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(object :
                ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    PersistentDataManager.updateConnection(true)
                    PersistentDataManager.requestUserDataUpdate()
                }

                override fun onLost(network: Network) {
                    PersistentDataManager.updateConnection(false)
                }
            })

        MapKitFactory.setApiKey("c161f4cb-68c2-4373-b0fb-dfa905b578d7")

        setContent {
            VecoApp(startScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        PersistentDataManager.updateConnection(connectivityManager.isDefaultNetworkActive)
        PersistentDataManager.requestUserDataUpdate()
    }
}

lateinit var testMaterialText: String // TEMP
