package linhdo.inface.db.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Entity(tableName = "user")
data class User @JvmOverloads constructor(@PrimaryKey
                                          @ColumnInfo(name = "user_id")
                                          @SerializedName("userId")
                                          var userId: String = "",
                                          @SerializedName("username") var username: String = "",
                                          @SerializedName("email") var email: String = "") : Serializable