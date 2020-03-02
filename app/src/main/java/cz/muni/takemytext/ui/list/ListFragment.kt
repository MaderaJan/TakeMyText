package cz.muni.takemytext.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cz.muni.takemytext.R
import cz.muni.takemytext.model.Note
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list, container, false).apply {

            val noteList = mutableListOf<Note>().apply {
                repeat(40) {
                    add(Note("text$it", it.toLong(), "category$it", "user$it"))
                }
            }

            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = NoteAdapter(noteList)
        }

}