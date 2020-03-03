package info.tduty.typetalk.data.db

import android.content.Context
import androidx.room.Room
import info.tduty.typetalk.BuildConfig

class DataBaseBuilderRoom(private val context: Context) : DataBaseBuilder {

    private var appDataBase: AppDatabase? = null

    override fun build(): AppDatabase? {
        appDataBase = Room.databaseBuilder(context, AppDatabase::class.java, BuildConfig.Database_name).build()
        return appDataBase
    }

    override fun migrate() {
        // TODO: migration db with recreation
    }
}
