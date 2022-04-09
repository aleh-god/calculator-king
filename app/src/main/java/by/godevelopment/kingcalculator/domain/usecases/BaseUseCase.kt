package by.godevelopment.kingcalculator.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<out Type, in Params> where Type : Any? {

    abstract suspend fun run(params: Params): Type

    fun execute(params: Params): Flow<Type> = flow {
        emit(run(params))
    }
}

object EmptyParams
