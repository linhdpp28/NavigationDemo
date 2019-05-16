package linhdo.inface.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import linhdo.inface.InApplication
import linhdo.inface.LoginErrMessage
import linhdo.inface.db.user.User
import linhdo.inface.extensions.emailValidator
import linhdo.inface.extensions.getFBAuth
import linhdo.inface.extensions.getFBRef
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

    fun login(account: Pair<String, String>) {
        val err = validateDetail(email = account.first, password = account.second)
        if (err.isNotEmpty()) {
            workStatus.postValue(NetworkState.error(err))
            return
        }
        InApplication.instance.getFBAuth().signInWithEmailAndPassword(account.first, account.second)
            .addOnSuccessListener {
                if (it.user != null) getUserDetail(it.user)
                workStatus.postValue(NetworkState.LOADED)
            }
            .addOnFailureListener {
                workStatus.postValue(NetworkState.error(it.localizedMessage))
            }
    }

    private fun getUserDetail(fbUser: FirebaseUser) {
        var user: User? = null
        InApplication.instance.getFBRef().child("users").equalTo(fbUser.uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(err: DatabaseError) {
                    workStatus.postValue(NetworkState.error(err.message))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val email = dataSnapshot.child("email").getValue(String::class.java) ?: return
                    val username = dataSnapshot.child("username").getValue(String::class.java) ?: return
                    user = User(fbUser.uid, username, email)
                }
            })
        viewModelScope.launch {
            repo?.insert(user ?: return@launch)
        }
    }

    fun register(detail: Pair<String, String>, passwords: Pair<String, String>) {
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
        InApplication.instance.getFBRef().child("users")
            .child(userId).let {
                it.child("email").setValue(email)
                it.child("username").setValue(username)
            }
    }
}