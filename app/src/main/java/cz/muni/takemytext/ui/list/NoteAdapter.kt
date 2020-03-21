package cz.muni.takemytext.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cz.muni.takemytext.R
import cz.muni.takemytext.extension.toBitmap
import cz.muni.takemytext.extension.toPresentableDate
import cz.muni.takemytext.model.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val notes: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes[position])
    }

    fun addNote(note: Note) {
        notes.add(note)
        notifyDataSetChanged()
    }

    fun submitList(notes: List<Note>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(note: Note) {
            view.note_desc_text_view.text = note.text
            view.category_text_view.text = note.category
            view.user_text_view.text = note.user
            view.date_text_view.text = note.date.toPresentableDate()
            view.image_view.setImageBitmap(note.imageByteArray?.toBitmap())
        }
    }
}