package cz.muni.takemytext.extension

import java.text.SimpleDateFormat
import java.util.*

fun Long.toPresentableDate(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(this)
}