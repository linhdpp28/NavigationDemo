package linhdo.inface.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    override fun onDestroy() {
        super.onDestroy()
        InApplication.instance.let {
            it.getSavedConfigPrefs().edit().apply {
                this.putString(ConfigKey.LANGUAGE_KEY, it.config.language)
            }.apply()
        }
    }
}
