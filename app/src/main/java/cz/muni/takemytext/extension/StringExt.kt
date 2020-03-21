package cz.muni.takemytext.extension

import java.text.SimpleDateFormat
import java.util.*

fun Long.toPresentableDate(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(this)
}