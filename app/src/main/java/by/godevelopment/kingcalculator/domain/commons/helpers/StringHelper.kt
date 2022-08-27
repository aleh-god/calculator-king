package by.godevelopment.kingcalculator.domain.commons.helpers

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringHelper @Inject constructor(
    @ApplicationContext val context: Context
) {
    fun getString(@StringRes stringId: Int): String =
        context.resources.getString(stringId)
}