package linhdo.inface.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import linhdo.inface.db.user.User
import linhdo.inface.db.user.UserDao

@Database(
    entities = arrayOf(User::class),
    version = 1,
    exportSchema = false
)
abstract class inFaceDB : RoomDatabase() {
    companion object {
        fun create(context: Context, useInMemory: Boolean): inFaceDB {
            val dbBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, inFaceDB::class.java)
            } else {
                Room.databaseBuilder(context, inFaceDB::class.java, "inface.db")
            }
            return dbBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun userDao(): UserDao
}