package uz.gita.memory_game_app.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.memory_game_app.data.models.GameModel
import uz.gita.memory_game_app.data.models.Level

interface AppRepository {
    fun loadDataByLevel(level: Level):Flow<List<GameModel>>
}