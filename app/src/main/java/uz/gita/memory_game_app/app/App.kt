package uz.gita.memory_game_app.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.gita.memory_game_app.data.SharedPref

@HiltAndroidApp
class App:Application() {
    override fun onCreate() {
        SharedPref.init(this)
        super.onCreate()

    }
}