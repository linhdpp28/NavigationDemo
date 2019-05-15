package linhdo.inface

enum class Language(val code: String) {
    Vietnamese("vi"),
    English("en")
}

object ConfigKey {
    const val LANGUAGE_KEY = "language_config"
}