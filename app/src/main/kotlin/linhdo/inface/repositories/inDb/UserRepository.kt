package linhdo.inface.repositories.inDb

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import linhdo.inface.db.user.User
import linhdo.inface.db.user.UserDao

class UserRepository(private val userDao: UserDao) {
    val user: LiveData<User> = userDao.getUser()

    @WorkerThread
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    @WorkerThread
    suspend fun delete() {
        userDao.deleteUser()
    }
}