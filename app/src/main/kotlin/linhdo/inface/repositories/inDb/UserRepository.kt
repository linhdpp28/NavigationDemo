package linhdo.inface.repositories.inDb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import linhdo.customviews.logging.InLogging
import linhdo.inface.db.user.User
import linhdo.inface.db.user.UserDao
import linhdo.inface.extensions.getFBDatabase

class UserRepository(private val userDao: UserDao) {
    private val usersCollection = getFBDatabase().collection("users")
    val user: LiveData<User>? = userDao.getUser()

    @WorkerThread
    suspend fun pushNewUser(user: User) {
        usersCollection.add(user.userId)
        usersCollection.document(user.userId).set(user)
    }

    @ExperimentalCoroutinesApi
    @WorkerThread
    suspend fun insert(fbUser: FirebaseUser) {
        usersCollection.document(fbUser.uid)
                .get()
                .addOnSuccessListener {
                    val user = User()
                    it.data?.forEach { data ->
                        InLogging.inInfo("${data.key} : ${data.value}")
                        when (data.key) {
                            "userId" -> user.userId = data.value as? String ?: ""
                            "username" -> user.username = data.value as? String ?: ""
                            "email" -> user.email = data.value as? String ?: ""
                        }
                    }
                    userDao.insert(user)
                }
                .addOnFailureListener {
                    InLogging.inError(message = it.localizedMessage)
                }
    }

    @WorkerThread
    suspend fun delete() {
        userDao.deleteUser()
    }
}