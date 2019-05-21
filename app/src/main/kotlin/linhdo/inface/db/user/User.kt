package linhdo.inface.db.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "User")
class User(@PrimaryKey var userId: String,
           var username: String,
           var email: String) : Serializable {
    constructor() : this("", "", "")
}