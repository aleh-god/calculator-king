package by.godevelopment.kingcalculator.commons

import android.text.format.DateFormat

fun Long.toDataString(): String {
    return DateFormat.format(DATE_FORMAT_TEMPLATE, this).toString()
}
