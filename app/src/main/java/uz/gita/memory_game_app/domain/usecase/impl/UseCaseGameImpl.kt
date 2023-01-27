package uz.gita.memory_game_app.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.memory_game_app.data.models.GameModel
import uz.gita.memory_game_app.data.models.Level
import uz.gita.memory_game_app.domain.repository.AppRepository
import uz.gita.memory_game_app.domain.usecase.UseCaseGame
import javax.inject.Inject

class UseCaseGameImpl @Inject constructor(private val appRepository: AppRepository) : UseCaseGame {


    override fun getDataByLevel(level: Level): Flow<List<GameModel>> = flow {
        val result = ArrayList<GameModel>()
        val list = appRepository.loadDataByLevel(level)

        list.collect{
            result.addAll(it)
            result.addAll(it)
        }
        result.shuffle()
        emit(result)
    }.flowOn(Dispatchers.Default)
}