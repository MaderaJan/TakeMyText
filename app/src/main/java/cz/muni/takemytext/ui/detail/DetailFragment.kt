package cz.muni.takemytext.ui.detail

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import cz.muni.takemytext.R
import cz.muni.takemytext.extension.toPresentableDate
import cz.muni.takemytext.model.Note
import kotlinx.android.synthetic.main.fragment_detail.view.*
import java.util.*

class DetailFragment : Fragment() {

    var note = Note("", 0L, "", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        view.title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                note = note.copy(text = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        view.date_text_view.text = System.currentTimeMillis().toPresentableDate()
        context?.let { context ->
            view.date_text_view.setOnClickListener {
                DatePickerDialog(
                    context,
                    DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                        val dateMillis = Calendar.getInstance().apply {
                            set(Calendar.YEAR, year)
                            set(Calendar.MONTH, month)
                            set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        }.timeInMillis

                        note = note.copy(date = dateMillis)
                        view.date_text_view.text = dateMillis.toPresentableDate()
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        }

        val categoryArray = arrayOf("Doma", "Práce", "Osobní")
        context?.let { context ->
            view.category_spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, categoryArray)

            view.category_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        note = note.copy(category = categoryArray[position])
                    }
                }
        }

        val usersSpinner = arrayOf("Honza", "Anna", "Bára")
        context?.let { context ->
            view.users_spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, usersSpinner)

            view.users_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        note = note.copy(user = usersSpinner[position])
                    }
                }
        }

        view.save_button.setOnClickListener {
            val intent = Intent()
            intent.putExtra(DetailActivity.ARG_NOTE, note)

            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }

        return view
    }
}