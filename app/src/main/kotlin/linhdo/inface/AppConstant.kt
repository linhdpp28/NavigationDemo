package linhdo.inface

enum class Language(val code: String) {
    Vietnamese("vi"),
    English("en")
}

object ConfigKey {
    const val LANGUAGE_KEY = "language_config"
}

object LoginErrMessage {
    const val EMPTY_USERNAME = "Username is empty!"
    const val EMPTY_EMAIL = "Email is empty!"
    const val EMPTY_PASSWORD = "Password is empty!"
    const val EMPTY_REPASSWORD = "Re-type password is empty!"
    const val WRONG_FORMAT_EMAIL = "Wrong email format!"
    const val SHORT_PASSWORD = "Password length must at least 6 digit."
    const val WRONG_REPASSWORD = "Password and Re-type Password is not same"
}