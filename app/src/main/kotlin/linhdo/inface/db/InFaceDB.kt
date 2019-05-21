package linhdo.inface.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import linhdo.inface.db.user.User
import linhdo.inface.db.user.UserDao

@Database(
        entities = [User::class],
        version = 2,
    exportSchema = false
)
abstract class InFaceDB : RoomDatabase() {
    companion object {
        fun create(context: Context, useInMemory: Boolean): InFaceDB {
            val dbBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, InFaceDB::class.java)
            } else {
                Room.databaseBuilder(context, InFaceDB::class.java, "inface.db")
            }
            return dbBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun userDao(): UserDao
}