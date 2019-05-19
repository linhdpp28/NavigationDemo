package linhdo.inface.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import linhdo.customviews.logging.InLogging
import linhdo.inface.InApplication
import linhdo.inface.LoginErrMessage
import linhdo.inface.db.user.User
import linhdo.inface.extensions.emailValidator
import linhdo.inface.extensions.getFBAuth
import linhdo.inface.extensions.getFBDatabase
import linhdo.inface.repositories.inDb.UserRepository
import linhdo.inface.utils.status.NetworkState

@ExperimentalCoroutinesApi
class AuthViewModel : ViewModel() {
    private var repo: UserRepository? = null
    fun setRepo(repository: UserRepository) {
        this.repo = repository
    }

    private val workStatus = MutableLiveData<NetworkState>()
    fun status(): LiveData<NetworkState> = workStatus


    private fun validateDetail(
            username: String? = null,
            email: String,
            password: String,
            rePassword: String? = null
    ): String {
        return when {
            username != null && username.isEmpty() -> LoginErrMessage.EMPTY_USERNAME
            email.isEmpty() -> LoginErrMessage.EMPTY_EMAIL
            !email.emailValidator() -> LoginErrMessage.WRONG_FORMAT_EMAIL
            password.isEmpty() -> LoginErrMessage.EMPTY_PASSWORD
            rePassword != null -> {
                when {
                    rePassword.isEmpty() -> LoginErrMessage.EMPTY_REPASSWORD
                    password.length < 6 -> LoginErrMessage.SHORT_PASSWORD
                    password != rePassword -> LoginErrMessage.WRONG_REPASSWORD
                    else -> ""
                }
            }
            else -> ""
        }
    }

    private fun validateForgotPasswordEmail(email: String) = when {
        email.isEmpty() -> LoginErrMessage.EMPTY_EMAIL
        !email.emailValidator() -> LoginErrMessage.WRONG_FORMAT_EMAIL
        else -> ""
    }

    fun login(account: Pair<String, String>) {
        workStatus.postValue(NetworkState.LOADING)
        val err = validateDetail(email = account.first, password = account.second)
        if (err.isNotEmpty()) {
            workStatus.postValue(NetworkState.error(err))
            return
        }
        InApplication.instance.getFBAuth().signInWithEmailAndPassword(account.first, account.second)
                .addOnSuccessListener {
                    viewModelScope.launch { if (it.user != null) getUserDetail(it.user) }
                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }

    private fun getUserDetail(fbUser: FirebaseUser) {
        InApplication.instance.getFBDatabase().collection("users").document(fbUser.uid).get()
                .addOnSuccessListener { snapshot ->
                    //TODO(Cannot access database on the main thread)
                    val user = User()
                    snapshot.data?.forEach {
                        InLogging.inInfo("${it.key} : ${it.value}")
                        when (it.key) {
                            "username" -> user.username = it.value as? String ?: ""
                            "userId" -> user.userId = it.value as? String ?: ""
                            "email" -> user.email = it.value as? String ?: ""
                        }
                    }
                    viewModelScope.launch {
                        repo?.insert(user)
                        workStatus.postValue(NetworkState.LOADED)
                    }
                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }

    fun register(detail: Pair<String, String>, passwords: Pair<String, String>) {
        workStatus.postValue(NetworkState.LOADING)
        val err = validateDetail(
                username = detail.first,
                email = detail.second,
                password = passwords.first,
                rePassword = passwords.second
        )
        if (err.isNotEmpty()) {
            workStatus.postValue(NetworkState.error(err))
            return
        }
        InApplication.instance.getFBAuth().createUserWithEmailAndPassword(detail.second, passwords.first)
                .addOnSuccessListener {
                    if (it.user != null) updateUserDetail(it.user.uid, username = detail.first, email = detail.second)
                    InApplication.instance.getFBAuth().signOut()
                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }

    private fun updateUserDetail(userId: String, username: String, email: String) {
        val user = InApplication.instance.getFBDatabase().collection("users")
        val data = User(userId, username, email)
        user.document(userId).set(data)
    }

    fun forgotPassword(email: String) {
        workStatus.postValue(NetworkState.LOADING)
        val err = validateForgotPasswordEmail(email)
        if (err.isNotEmpty()) {
            workStatus.postValue(NetworkState.error(err))
            return
        }

        InApplication.instance.getFBAuth().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    workStatus.postValue(NetworkState.LOADED)
                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }
}