package info.tduty.typetalk.data.db

import android.content.Context
import androidx.room.Room

class DataBaseBuilderRoom(private val context: Context) : DataBaseBuilder {


    private val NAME_DATABASE: String = "type-talk-db"
    var appDataBase: AppDatabase? = null

    override fun build(): AppDatabase? {
        appDataBase = Room.databaseBuilder(context, AppDatabase::class.java, NAME_DATABASE).build()
        return appDataBase
    }

    override fun migrate() {
        // TODO: migration db with recreation
    }
}