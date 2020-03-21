package cz.muni.takemytext.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.muni.takemytext.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    // TODO GET ALL NOTES
    @Query("SELECT * FROM note")
    fun getAllNotes(): List<Note>
}