package cz.muni.takemytext.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cz.muni.takemytext.database.dao.NoteDao
import cz.muni.takemytext.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME = "takemytext.db"

        fun getDatabase(context: Context): NoteDatabase =
            Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

    }
}