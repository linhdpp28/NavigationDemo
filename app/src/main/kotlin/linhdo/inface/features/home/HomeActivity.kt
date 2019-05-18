package linhdo.inface.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_home.*
import linhdo.inface.ConfigKey
import linhdo.inface.InApplication
import linhdo.inface.R
import linhdo.inface.extensions.getSavedConfigPrefs


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
        }

        toolbar?.setOnLeftClickListener {
            if (it == R.drawable.ic_menu) {
                drawer?.openDrawer(GravityCompat.START)
            } else onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        InApplication.instance.getSavedConfigPrefs().edit {
            this.putString(ConfigKey.LANGUAGE_KEY, InApplication.instance.config.language)
        }
    }
}