package cz.muni.takemytext.ui.detail

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import cz.muni.takemytext.R
import cz.muni.takemytext.extension.toByteArray
import cz.muni.takemytext.extension.toPresentableDate
import cz.muni.takemytext.model.Note
import cz.muni.takemytext.model.REQUEST_CAMERA_PERMISSION
import cz.muni.takemytext.model.REQUEST_IMAGE_CAPTURE
import kotlinx.android.synthetic.main.fragment_detail.view.*
import java.util.*

class DetailFragment : Fragment() {

    private var note = Note()

    // TODO USER repo
//    private val userRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
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

        // TODO ++Internet persmission
//        userRepository.fetchUsers { names ->
//            val usersSpinner = names?.toTypedArray() ?: arrayOf("Honza", "Anna", "Bára")
//
//            context?.let { context ->
//                view.users_spinner.adapter =
//                    ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, usersSpinner)
//
//                view.users_spinner.onItemSelectedListener =
//                    object : AdapterView.OnItemSelectedListener {
//                        override fun onNothingSelected(parent: AdapterView<*>?) {}
//                        override fun onItemSelected(
//                            parent: AdapterView<*>?,
//                            view: View?,
//                            position: Int,
//                            id: Long
//                        ) {
//                            note = note.copy(user = usersSpinner[position])
//                        }
//                    }
//            }
//        }

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

        view.add_photo.setOnClickListener {
            context?.let { context ->
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                } else {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }
            }
        }

        return view
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val bitmap = data?.extras?.get("data") as Bitmap?
                if (bitmap != null) {
                    view?.image_view?.setImageBitmap(bitmap)
                    note = note.copy(imageByteArray = bitmap.toByteArray())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            dispatchTakePictureIntent()
        }
    }
}