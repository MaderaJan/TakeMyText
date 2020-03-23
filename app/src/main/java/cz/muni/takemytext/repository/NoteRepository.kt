package cz.muni.takemytext.repository

import android.content.Context
import cz.muni.takemytext.database.NoteDatabase
import cz.muni.takemytext.database.dao.NoteDao
import cz.muni.takemytext.model.Note

class NoteRepository(private val context: Context) {

    private val noteDao: NoteDao by lazy { NoteDatabase.getDatabase(context).noteDao() }

    fun insertNote(note: Note) {
        noteDao.insert(note)
    }
}