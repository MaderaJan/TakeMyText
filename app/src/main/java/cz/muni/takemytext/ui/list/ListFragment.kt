package cz.muni.takemytext.ui.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.takemytext.R
import cz.muni.takemytext.extension.toPresentableDate
import cz.muni.takemytext.model.Note
import cz.muni.takemytext.repository.NoteRepository
import cz.muni.takemytext.ui.detail.DetailActivity
import cz.muni.takemytext.util.PrefManager
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private val adapter = NoteAdapter()
    private val prefManager: PrefManager? by lazy { context?.let { PrefManager(it) } }
    private val noteRepository: NoteRepository? by lazy { context?.let { NoteRepository(it) } }

    companion object {
        const val REQ_NOTE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list, container, false).apply {
            last_app_start_text_view.text = prefManager?.lastAppStartDate?.toPresentableDate()

            recycler_view.layoutManager = LinearLayoutManager(context)

            val notes = noteRepository?.getAllNotes() ?: listOf()

            adapter.submitList(notes)
            recycler_view.adapter = adapter

            add_button.setOnClickListener {
                startActivityForResult(DetailActivity.newIntent(context), REQ_NOTE)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQ_NOTE -> {
                val note = data?.getParcelableExtra<Note>(DetailActivity.ARG_NOTE) ?: return
                adapter.addNote(note)

                noteRepository?.insertNote(note)
            }
        }
    }
}