package linhdo.inface.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import linhdo.inface.db.user.User
import linhdo.inface.extensions.getFBAuth
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

    fun getUserDetail(): LiveData<User>? = userRepo?.user

    fun logout() {
        getFBAuth().signOut()
        viewModelScope.launch {
            userRepo?.delete()
        }
    }
}