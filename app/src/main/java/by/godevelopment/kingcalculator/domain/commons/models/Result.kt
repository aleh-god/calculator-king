package by.godevelopment.kingcalculator.domain.commons.models

import androidx.annotation.StringRes

sealed interface ResultDataBase<T> {
    data class Success<T>(val value: T) : ResultDataBase<T>
    data class Error<T>(@StringRes val message: Int) : ResultDataBase<T>
}