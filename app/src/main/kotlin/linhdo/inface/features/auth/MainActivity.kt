package linhdo.inface.features.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_main.*
import linhdo.inface.ConfigKey
import linhdo.inface.InApplication
import linhdo.inface.R
import linhdo.inface.extensions.getSavedConfigPrefs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
        }
    }

    fun showLoading() {
        progressBar?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progressBar?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        InApplication.instance.getSavedConfigPrefs().edit {
            this.putString(ConfigKey.LANGUAGE_KEY, InApplication.instance.config.language)
        }
    }
}
