package cz.muni.takemytext.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.muni.takemytext.R
import cz.muni.takemytext.util.PrefManager

class ListActivity: AppCompatActivity() {

    private val prefManager: PrefManager? by lazy { PrefManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        prefManager?.lastAppStartDate = System.currentTimeMillis()

        val fragment = ListFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}