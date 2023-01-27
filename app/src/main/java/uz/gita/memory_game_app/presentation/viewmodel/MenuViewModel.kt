package uz.gita.memory_game_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.memory_game_app.data.models.Level

interface MenuViewModel {

    val openGameLiveData:LiveData<Level>

    fun openGame(level: Level)
}