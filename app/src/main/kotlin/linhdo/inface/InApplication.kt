package linhdo.inface

import android.app.Application
import linhdo.inface.extensions.getSavedConfigPrefs

class InApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var instance: InApplication
            private set
    }

    val config: Config = Config()

    override fun onCreate() {
        super.onCreate()
        instance = this
        config.apply {
            language =
                getSavedConfigPrefs().getString(ConfigKey.LANGUAGE_KEY, Language.English.code) ?: Language.English.code
        }
    }

    data class Config(val version: String = BuildConfig.VERSION_NAME) {
        var language: String = Language.English.code
    }
}