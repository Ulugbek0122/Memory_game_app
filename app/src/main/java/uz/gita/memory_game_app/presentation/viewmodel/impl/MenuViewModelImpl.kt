package uz.gita.memory_game_app.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.memory_game_app.data.models.Level
import uz.gita.memory_game_app.presentation.viewmodel.MenuViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModelImpl @Inject constructor(): MenuViewModel,ViewModel() {

    override val openGameLiveData = MutableLiveData<Level>()


    override fun openGame(level: Level) {
        openGameLiveData.value = level
    }
}