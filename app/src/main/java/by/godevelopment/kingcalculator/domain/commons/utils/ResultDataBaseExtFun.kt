package by.godevelopment.kingcalculator.domain.commons.utils

import android.util.Log
import androidx.annotation.StringRes
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase

suspend fun <T, K> wrapResultBy(
    key: K,
    @StringRes
    message: Int = R.string.message_error_bad_database,
    block: suspend (K) -> T?
): ResultDataBase<T> {
    return try {
        val result = block.invoke(key)
        if (result != null) ResultDataBase.Success<T>(value = result)
        else ResultDataBase.Error<T>(message = R.string.message_error_bad_database)
    }
    catch (e: Exception) {
        Log.i(TAG, "wrapResultBy: ${e.message}")
        ResultDataBase.Error<T>(message = message)
    }
}

inline fun <T, R> ResultDataBase<T>.mapResult(transform: (T) -> R): ResultDataBase<R> {
    return when(this) {
        is ResultDataBase.Error -> ResultDataBase.Error<R>(message = this.message)
        is ResultDataBase.Success -> {
            ResultDataBase.Success<R>(value = transform.invoke(this.value))
        }
    }
}

inline fun <T, R> ResultDataBase<T>.flatMapResult(transform: (T) -> ResultDataBase<R>): ResultDataBase<R> {
    return when(this) {
        is ResultDataBase.Error<T> -> ResultDataBase.Error<R>(message = this.message)
        is ResultDataBase.Success<T> -> { transform.invoke(this.value) }
    }
}
