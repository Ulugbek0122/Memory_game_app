package uz.gita.memory_game_app.presentation.ui

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.memory_game_app.R
import uz.gita.memory_game_app.data.SharedPref
import uz.gita.memory_game_app.databinding.DialogAboutBinding
import uz.gita.memory_game_app.databinding.FragmentMenuScreen1Binding
import uz.gita.memory_game_app.databinding.SettingsDialogBinding


class MenuScreen1 : Fragment(R.layout.fragment_menu_screen1) {

    private val viewBinding: FragmentMenuScreen1Binding by viewBinding(FragmentMenuScreen1Binding::bind)

    private val sharedPref = SharedPref.getInstanse()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewBinding.play.setOnClickListener {
            findNavController().navigate(MenuScreen1Directions.actionMenuScreen12ToMenuScreen())
        }

        viewBinding.about.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            var binding = DialogAboutBinding.inflate(layoutInflater)
            builder.setView(binding.root)
            var aleartDialog = builder.create()

            binding.apply {
                btnOk.setOnClickListener {
                    aleartDialog.dismiss()
                }
            }
            aleartDialog.window!!.setBackgroundDrawable(
                ColorDrawable(
                    Color.TRANSPARENT
                )
            )
            aleartDialog.show()
        }


        viewBinding.btnSettings.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            var binding = SettingsDialogBinding.inflate(layoutInflater)
            builder.setView(binding.root)
            var aleartDialog = builder.create()
            binding.switchSound.isChecked = sharedPref.getMusic()

            binding.apply {
                switchSound.setOnCheckedChangeListener { _, b ->
                    switchSound.isChecked = b
                    sharedPref.setMusic(b)
                }

                btnOk.setOnClickListener {
                    aleartDialog.dismiss()
                }
            }
            aleartDialog.window!!.setBackgroundDrawable(
                ColorDrawable(
                    Color.TRANSPARENT
                )
            )
            aleartDialog.show()
        }

    }
}