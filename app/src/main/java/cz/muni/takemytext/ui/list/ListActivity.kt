package cz.muni.takemytext.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.muni.takemytext.R

class ListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        val fragment = ListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}