package cz.muni.takemytext.model

data class Note(
    val text: String,
    val date: Long,
    val category: String,
    val user: String
)