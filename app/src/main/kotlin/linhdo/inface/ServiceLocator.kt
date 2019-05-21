package linhdo.inface

import android.app.Application
import android.content.Context
import linhdo.inface.db.InFaceDB
import linhdo.inface.repositories.inDb.UserRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

interface ServiceLocator {
    companion object {
        private val LOCK = Any()

        private var instance: ServiceLocator? = null
        fun instance(context: Context): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(
                        app = context.applicationContext as Application,
                        useInMemoryDb = false
                    )
                }
                return instance!!
            }
        }
    }

    fun getNetworkExecutor(): Executor

    fun getDiskIOExecutor(): Executor

    fun getUserRepo(): UserRepository
}

open class DefaultServiceLocator(val app: Application, val useInMemoryDb: Boolean) : ServiceLocator {
    // thread pool used for disk access
    @Suppress("PrivatePropertyName")
    private val DISK_IO = Executors.newSingleThreadExecutor()

    // thread pool used for network requests
    @Suppress("PrivatePropertyName")
    private val NETWORK_IO = Executors.newFixedThreadPool(5)

    private val db by lazy {
        InFaceDB.create(app, useInMemoryDb)
    }

    override fun getNetworkExecutor(): Executor = NETWORK_IO

    override fun getDiskIOExecutor(): Executor = DISK_IO

    override fun getUserRepo(): UserRepository = UserRepository(db.userDao())
}