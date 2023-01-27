package uz.gita.memory_game_app.presentation.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memory_game_app.R
import uz.gita.memory_game_app.data.SharedPref
import uz.gita.memory_game_app.data.models.Level
import uz.gita.memory_game_app.databinding.DialogWinBinding
import uz.gita.memory_game_app.databinding.FragmentMenuScreenBinding
import uz.gita.memory_game_app.databinding.SettingsDialogBinding
import uz.gita.memory_game_app.presentation.viewmodel.MenuViewModel
import uz.gita.memory_game_app.presentation.viewmodel.impl.MenuViewModelImpl

@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.fragment_menu_screen) {

    private val viewModel: MenuViewModel by viewModels<MenuViewModelImpl>()
    private val viewBinding: FragmentMenuScreenBinding by viewBinding(FragmentMenuScreenBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openGameLiveData.observe(this, openGameObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.btnEasy.setOnClickListener { viewModel.openGame(Level.EASY) }
        viewBinding.btnMedium.setOnClickListener { viewModel.openGame(Level.MEDIUM) }
        viewBinding.btnHard.setOnClickListener { viewModel.openGame(Level.HARD) }
    }

    private val openGameObserver = Observer<Level> {
        findNavController().navigate(MenuScreenDirections.actionMenuScreenToGameScreen(it))
    }
}