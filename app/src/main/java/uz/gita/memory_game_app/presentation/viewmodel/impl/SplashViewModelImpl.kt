package uz.gita.memory_game_app.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memory_game_app.presentation.viewmodel.SplashViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModelImpl @Inject constructor(): SplashViewModel,ViewModel() {

    override val openMenuLiveData = MutableLiveData<Unit>()

    init {
        viewModelScope.launch {
            delay(4000)
            openMenuLiveData.value = Unit
        }
    }

}