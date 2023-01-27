package uz.gita.memory_game_app.presentation.ui

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.*
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memory_game_app.R
import uz.gita.memory_game_app.data.SharedPref
import uz.gita.memory_game_app.data.models.GameModel
import uz.gita.memory_game_app.data.models.Level
import uz.gita.memory_game_app.databinding.DialogGameOverBinding
import uz.gita.memory_game_app.databinding.DialogWinBinding
import uz.gita.memory_game_app.databinding.FragmentGameScreenBinding
import uz.gita.memory_game_app.presentation.viewmodel.GameViewModel
import uz.gita.memory_game_app.presentation.viewmodel.impl.GameViewModelImpl
import uz.gita.memory_game_app.utils.ExplosionField


@AndroidEntryPoint
class GameScreen : Fragment(R.layout.fragment_game_screen) {

    private val viewModel: GameViewModel by viewModels<GameViewModelImpl>()
    private val viewBinding: FragmentGameScreenBinding by viewBinding(FragmentGameScreenBinding::bind)


    private val sharedPref = SharedPref.getInstanse()
    private var level = Level.EASY
    private var _width = 0
    private var _height = 0
    private var count = 0
    private var cliCount = 0
    private var countWin = 0
    private var score1 = 0
    private lateinit var time: CountDownTimer
    private var list = ArrayList<ImageView>()
    private lateinit var images: ArrayList<ImageView>
    private val args: GameScreenArgs by navArgs()

    private lateinit var mediaPlayer: MediaPlayer

    private var bulbTrue = false
    private lateinit var list1: List<GameModel>
    private var timer: Long = 0

    private var timer1: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.music)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        score1 = sharedPref.getScore()
        viewBinding.score.text = score1.toString()
        args.level.apply {
            level = this
            count = x * y
        }


        images = ArrayList(count)
        viewBinding.containerMain.post {
            _width = viewBinding.containerMain.width / (level.x)
            _height = viewBinding.containerMain.height / (level.y) - 10

            viewBinding.containerImage.layoutParams.apply {
                width = _width * level.x
                height = _height * level.y
            }
            loadImages()
        }
        viewModel.getDataByLevel(level)
        viewModel.gameModelLiveData.observe(viewLifecycleOwner, gameDataObserver)
        viewBinding.progressBar.max = level.time.toInt()
        time = object : CountDownTimer(level.time, 5) {

            override fun onTick(millisUntilFinished: Long) {
                timer = millisUntilFinished
                viewBinding.progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                val builder = AlertDialog.Builder(requireContext())
                var binding = DialogGameOverBinding.inflate(layoutInflater)
                builder.setView(binding.root)
                var aleartDialog = builder.create()
                binding.btnRestart.setOnClickListener {
                    time.cancel()
                    list.clear()
                    closeImage(list1)
                    cliCount = 0
                    countWin = 0
                    images.clear()
                    loadImages()
                    aleartDialog.dismiss()
                    restart(list1)

                }
                binding.btnQuit.setOnClickListener {
                    aleartDialog.dismiss()
                    findNavController().navigateUp()
                }
                aleartDialog.window!!.setBackgroundDrawable(
                    ColorDrawable(
                        android.graphics.Color.TRANSPARENT
                    )
                )
                aleartDialog.show()
            }
        }.start()

        if (sharedPref.getMusic()) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        }

        viewBinding.bulb.setOnClickListener {
            if (score1 >= 500) {
                openImages(list1)
                score1 -= 500
                viewBinding.score.text = score1.toString()
            }

        }
    }


    private fun closeImage(item: List<GameModel>) {
        for (i in item.indices) {
            val imageView = images[i]
            imageView.visibility = View.INVISIBLE
        }
    }

    private fun loadImages() {
        for (y in 0 until level.y) {
            for (x in 0 until level.x) {
                val imageView = ImageView(requireContext())
                viewBinding.containerImage.addView(imageView)
                val lp = imageView.layoutParams as RelativeLayout.LayoutParams

                lp.apply {
                    width = _width
                    height = _height
                }

                imageView.x = x * _width * 1f
                imageView.y = y * _height * 1f
                imageView.setPadding(10, 10, 10, 10)
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.layoutParams = lp
                imageView.setImageResource(R.drawable.question3)
                images.add(imageView)
            }
        }
    }


    override fun onPause() {
        sharedPref.setScore(score1)
        time.cancel()
        mediaPlayer.pause()
        super.onPause()
    }

    override fun onResume() {
        if (timer != 0L) {
            Log.d("EEE","$timer")
            time = object : CountDownTimer(timer, 5) {

                override fun onTick(millisUntilFinished: Long) {
                    timer = millisUntilFinished
                    viewBinding.progressBar.progress = millisUntilFinished.toInt()
                }

                override fun onFinish() {
                    val builder = AlertDialog.Builder(requireContext())
                    var binding = DialogGameOverBinding.inflate(layoutInflater)
                    builder.setView(binding.root)
                    var aleartDialog = builder.create()
                    binding.btnRestart.setOnClickListener {
                        time.cancel()
                        list.clear()
                        closeImage(list1)
                        cliCount = 0
                        countWin = 0
                        images.clear()
                        loadImages()
                        aleartDialog.dismiss()
                        restart(list1)

                    }
                    binding.btnQuit.setOnClickListener {
                        aleartDialog.dismiss()
                        findNavController().navigateUp()
                    }
                    aleartDialog.window!!.setBackgroundDrawable(
                        ColorDrawable(
                            android.graphics.Color.TRANSPARENT
                        )
                    )
                    aleartDialog.show()
                }
            }
            time.start()

        }
        if (sharedPref.getMusic()) {
            mediaPlayer.start()
        }
        super.onResume()
    }

    private val gameDataObserver = Observer<List<GameModel>> { item ->
        list1 = item
        openImages(item)
        for (i in item.indices) {
            val imageView = images[i]
            imageView.tag = item[i]

            imageView.setOnClickListener {
                if (it.rotationY == 0f) {
                    if (cliCount == 1) {
                        openView(imageView)
                        cliCount++
                        val imageView1 = list[0]
                        if (imageView1.tag == imageView.tag) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(1500)
                                val mExplosionField = ExplosionField.attach2Window(activity)
                                mExplosionField.explode(imageView)
                                mExplosionField.explode(imageView1)
                                imageView.visibility = View.INVISIBLE
                                imageView1.visibility = View.INVISIBLE
                                countWin += 2
                                score1 += 100
                                viewBinding.score.text = score1.toString()
                                if (countWin == count) {
                                    val builder = AlertDialog.Builder(requireContext())
                                    var binding = DialogWinBinding.inflate(layoutInflater)
                                    builder.setView(binding.root)
                                    var aleartDialog = builder.create()

                                    binding.btnNext.setOnClickListener {
                                        time.cancel()
                                        list.clear()
                                        cliCount = 0
                                        countWin = 0
                                        images.clear()
                                        loadImages()
                                        aleartDialog.dismiss()
                                        restart(item)
                                    }
                                    binding.btnQuit.setOnClickListener {
                                        aleartDialog.dismiss()
                                        findNavController().navigateUp()

                                    }
                                    aleartDialog.window!!.setBackgroundDrawable(
                                        ColorDrawable(
                                            android.graphics.Color.TRANSPARENT
                                        )
                                    )
                                    aleartDialog.show()
                                }
                                list.clear()
                                cliCount = 0
                            }

                        } else {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(500)
                                vibratePhone()
                                shakeView(imageView)
                                shakeView(imageView1)
                                delay(1000)
                                closeView(imageView)
                                closeView(imageView1)
                                list.clear()
                                cliCount = 0
                            }

                        }
                    } else if (cliCount < 1) {
                        openView(imageView)
                        list.add(imageView)
                        cliCount++
                    }


                }
            }
        }
    }


    private fun openImages(list: List<GameModel>) {
        for (i in list.indices) {
            val imageView = images[i]
            openView(imageView)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1500)
            for (i in list.indices) {
                delay(130)
                val imageView = images[i]
                closeView(imageView)
            }
        }
    }

    fun closeView(imageView: ImageView) {
        imageView.animate()
            .setDuration(200)
            .rotationY(90f)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                imageView.setImageResource(R.drawable.question3)
                imageView.animate()
                    .setDuration(200)
                    .rotationY(0f)
                    .start()
            }.start()
    }

    private fun openView(imageView: ImageView) {
        imageView.animate()
            .setDuration(200)
            .rotationY(90f)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                imageView.setImageResource((imageView.tag as GameModel).Image)
                imageView.animate()
                    .setDuration(200)
                    .rotationY(180f)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
            }.start()
    }

    fun shakeView(view: View) {
        view.animate()
            .setDuration(50)
            .xBy(8f)
            .withEndAction {
                view.animate()
                    .setDuration(50)
                    .xBy(-16f)
                    //.setInterpolator(DecelerateInterpolator())
                    .withEndAction {
                        view.animate()
                            .setDuration(50)
                            .xBy(16f)
                            .withEndAction {
                                view.animate()
                                    .setDuration(50)
                                    .xBy(-8f)
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }
            .start()
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    500,
                    VibrationEffect.CONTENTS_FILE_DESCRIPTOR
                )
            )
        } else {
            vibrator.vibrate(500)
        }
    }


    fun restart(item: List<GameModel>) {
        openImages(item)
        for (i in item.indices) {
            val imageView = images[i]
            imageView.tag = item[i]

            viewBinding.progressBar.max = level.time.toInt()
            time.onTick(level.time)
            viewBinding.progressBar.progress = level.time.toInt()
            time.start()
//            time = object : CountDownTimer(level.time, 5) {
//
//                override fun onTick(millisUntilFinished: Long) {
//                    viewBinding.progressBar.progress = millisUntilFinished.toInt()
//                }
//                override fun onFinish() {
//                    val builder = AlertDialog.Builder(requireContext())
//                    var binding = DialogGameOverBinding.inflate(layoutInflater)
//                    builder.setView(binding.root)
//                    var aleartDialog = builder.create()
//
//                    binding.btnRestart.setOnClickListener {
//                        list.clear()
//                        cliCount = 0
//
//                        viewBinding.score.text = "0"
//                        images.clear()
//                        loadImages()
//                        viewModel.gameModelLiveData.observe(viewLifecycleOwner){
//                            aleartDialog.dismiss()
//                            restart(it)
//                        }
//
//                    }
//                    binding.btnQuit.setOnClickListener {
//                        findNavController().navigateUp()
//                        aleartDialog.dismiss()
//                    }
//                    aleartDialog.window!!.setBackgroundDrawable(
//                        ColorDrawable(
//                            android.graphics.Color.TRANSPARENT
//                        )
//                    )
//                    aleartDialog.show()
//                }
//            }.start()

            imageView.setOnClickListener {
                if (it.rotationY == 0f) {
                    if (cliCount == 1) {
                        openView(imageView)
                        cliCount++
                        val imageView1 = list[0]
                        if (imageView1.tag == imageView.tag) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(1500)
                                val mExplosionField = ExplosionField.attach2Window(activity)
                                mExplosionField.explode(imageView)
                                mExplosionField.explode(imageView1)
                                imageView.visibility = View.INVISIBLE
                                imageView1.visibility = View.INVISIBLE
                                countWin += 2
                                score1 += 100
                                viewBinding.score.text = score1.toString()
                                if (countWin == count) {
                                    val builder = AlertDialog.Builder(requireContext())
                                    var binding = DialogWinBinding.inflate(layoutInflater)
                                    builder.setView(binding.root)
                                    var aleartDialog = builder.create()

                                    binding.btnNext.setOnClickListener {
                                        list.clear()
                                        cliCount = 0
                                        countWin = 0
                                        images.clear()
                                        loadImages()
                                        restart(item)
                                        aleartDialog.dismiss()
                                    }
                                    binding.btnQuit.setOnClickListener {
                                        aleartDialog.dismiss()
                                        findNavController().navigateUp()

                                    }
                                    aleartDialog.window!!.setBackgroundDrawable(
                                        ColorDrawable(
                                            android.graphics.Color.TRANSPARENT
                                        )
                                    )
                                    aleartDialog.show()
                                }
                                list.clear()
                                cliCount = 0
                            }

                        } else {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(500)
                                vibratePhone()
                                shakeView(imageView)
                                shakeView(imageView1)
                                delay(1000)
                                closeView(imageView)
                                closeView(imageView1)
                                list.clear()
                                cliCount = 0
                            }

                        }
                    } else if (cliCount < 1) {
                        openView(imageView)
                        list.add(imageView)
                        cliCount++
                    }


                }
            }
        }
    }
}