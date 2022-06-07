package by.godevelopment.kingcalculator.commons

import android.text.format.DateFormat

fun Long.toDataString(): String {
    return DateFormat.format("hh:mm:ss, MMM dd, yyyy", this).toString()
}