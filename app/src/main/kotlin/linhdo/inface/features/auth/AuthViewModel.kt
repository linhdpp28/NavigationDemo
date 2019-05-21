package linhdo.inface.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import linhdo.inface.LoginErrMessage
import linhdo.inface.db.user.User
import linhdo.inface.extensions.emailValidator
import linhdo.inface.extensions.getFBAuth
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
        getFBAuth().signInWithEmailAndPassword(account.first, account.second)
                .addOnSuccessListener {
                    viewModelScope.launch { if (it.user != null) getUserDetail(it.user) }

                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }

    private suspend fun getUserDetail(fbUser: FirebaseUser) {
        repo?.insert(fbUser)
        workStatus.postValue(NetworkState.LOADED)
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
        getFBAuth().createUserWithEmailAndPassword(detail.second, passwords.first)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        if (it.user != null)
                            repo?.pushNewUser(User(it.user.uid, email = detail.second, username = detail.first))
                    }
                    getFBAuth().signOut()
                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }

    fun forgotPassword(email: String) {
        workStatus.postValue(NetworkState.LOADING)
        val err = validateForgotPasswordEmail(email)
        if (err.isNotEmpty()) {
            workStatus.postValue(NetworkState.error(err))
            return
        }

        getFBAuth().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    workStatus.postValue(NetworkState.LOADED)
                }
                .addOnFailureListener {
                    workStatus.postValue(NetworkState.error(it.localizedMessage))
                }
    }
}