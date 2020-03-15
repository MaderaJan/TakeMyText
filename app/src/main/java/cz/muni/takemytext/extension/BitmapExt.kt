package cz.muni.takemytext.extension

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

// TODO
fun Bitmap.toByteArray(): ByteArray {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

// TODO
fun ByteArray.toBitmap(): Bitmap =
    BitmapFactory.decodeByteArray(this, 0, this.size)