package cz.muni.takemytext.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String = "",
    val date: Long = 0L,
    val category: String = "",
    val user: String = "",
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imageByteArray: ByteArray? = null
) : Parcelable