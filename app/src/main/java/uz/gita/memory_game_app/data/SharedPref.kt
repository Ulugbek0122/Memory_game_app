package uz.gita.memory_game_app.data

import android.content.Context
import android.util.Log

class SharedPref private constructor(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("LOCAL", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    companion object {
        private var localDataSourse: SharedPref? = null

        fun init(context: Context) {
            if (localDataSourse == null) {
                Log.d("PPP","sssss")
                localDataSourse = SharedPref(context)
            }
        }

        fun getInstanse() = localDataSourse!!
    }

    fun setMusic(isTrue: Boolean) {
        editor.putBoolean("music", isTrue)
        editor.apply()
        Log.d("PPP","sssss")
    }

    fun getMusic():Boolean {
        return sharedPreferences.getBoolean("music",false)
    }

    fun setScore(score: Int){
        editor.putInt("score", score)
        editor.apply()
    }

    fun getScore():Int{
        return sharedPreferences.getInt("score",0)
    }

    fun setTimer(timer:Long){
        editor.putLong("timer",timer)
        editor.apply()
    }

    fun getTimer():Long{
        return sharedPreferences.getLong("timer",-1L)
    }

}