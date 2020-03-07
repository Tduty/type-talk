package info.tduty.typetalk.data.db

interface DataBaseBuilder {
    fun build(): AppDatabase
    fun migrate()
}
