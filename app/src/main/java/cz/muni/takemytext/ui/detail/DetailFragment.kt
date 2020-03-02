package cz.muni.takemytext.ui.detail

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import cz.muni.takemytext.R
import cz.muni.takemytext.extension.toPresentableDate
import kotlinx.android.synthetic.main.fragment_detail.view.*
import java.util.*

class DetailFragment : Fragment() {

    var text = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        view.title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                text = s.toString()
                Log.d("DETAIL_FRAGMENT", text)
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
        }

        val usersSpinner = arrayOf("Honza", "Anna", "Bára")
        context?.let { context ->
            view.users_spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, usersSpinner)
        }

        view.save_button.setOnClickListener {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
//            Log.d("DETAIL_FRAGMENT", "on button clicked ${text.length}")
        }

        return view
    }
}