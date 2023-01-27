package uz.gita.memory_game_app.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.memory_game_app.data.models.GameModel
import uz.gita.memory_game_app.data.models.Level

interface GameViewModel {

    val gameModelLiveData:LiveData<List<GameModel>>

    fun getDataByLevel(level: Level)

    fun btnClicked(state:Boolean,position:Int)
}