package cz.muni.takemytext.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Note(
    val text: String,
    val date: Long,
    val category: String,
    val user: String
) : Parcelable