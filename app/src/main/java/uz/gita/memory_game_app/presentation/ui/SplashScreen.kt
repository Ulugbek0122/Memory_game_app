package uz.gita.memory_game_app.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memory_game_app.R
import uz.gita.memory_game_app.databinding.FragmentSplashScreenBinding
import uz.gita.memory_game_app.presentation.viewmodel.SplashViewModel
import uz.gita.memory_game_app.presentation.viewmodel.impl.SplashViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.fragment_splash_screen) {

    private val viewModel:SplashViewModel by viewModels<SplashViewModelImpl>()
    private val viewBinding:FragmentSplashScreenBinding by viewBinding(FragmentSplashScreenBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openMenuLiveData.observe(this,openMenuObserver)
    }

    private val openMenuObserver = Observer<Unit>{
        findNavController().navigate(SplashScreenDirections.actionSplashScreen2ToMenuScreen12())
    }
}