package cz.muni.takemytext.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cz.muni.takemytext.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_single_container)

        val fragment = DetailFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}