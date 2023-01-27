package uz.gita.memory_game_app.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.memory_game_app.data.models.GameModel
import uz.gita.memory_game_app.data.models.Level
import uz.gita.memory_game_app.domain.usecase.UseCaseGame
import uz.gita.memory_game_app.presentation.viewmodel.GameViewModel
import javax.inject.Inject
@HiltViewModel
class GameViewModelImpl @Inject constructor(private val useCaseGame: UseCaseGame): GameViewModel,ViewModel() {

    override val gameModelLiveData = MutableLiveData<List<GameModel>>()


    override fun getDataByLevel(level: Level) {
        useCaseGame.getDataByLevel(level)
            .onEach {
                gameModelLiveData.postValue(it)
            }
            .launchIn(viewModelScope)
    }

    override fun btnClicked(state: Boolean, position: Int) {

    }
}