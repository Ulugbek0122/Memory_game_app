package uz.gita.memory_game_app.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.memory_game_app.data.models.GameModel
import uz.gita.memory_game_app.data.models.Level

interface UseCaseGame {

    fun getDataByLevel(level:Level):Flow<List<GameModel>>
}