package linhdo.inface.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import linhdo.inface.db.user.User
import linhdo.inface.repositories.inDb.UserRepository

/***
 * @author: Lac
 * 19-May-19 - 11:45 PM
 */
class HomeViewModel : ViewModel() {
    private var userRepo: UserRepository? = null
    fun setUserRepo(repository: UserRepository) {
        this.userRepo = repository
    }

    fun getUserDetail(): LiveData<User?>? {
        return userRepo?.user
    }
}