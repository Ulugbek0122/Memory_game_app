package uz.gita.memory_game_app.data.models

enum class Level(val x: Int, val y: Int, val time: Long , val showTime : Long) {
        EASY(3, 4, 60_000,1000),
        MEDIUM(4, 5, 150_000,1800),
        HARD(5, 6, 300_000,2500)
}