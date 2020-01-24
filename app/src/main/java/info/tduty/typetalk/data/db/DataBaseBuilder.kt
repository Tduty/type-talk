package info.tduty.typetalk.data.db

interface DataBaseBuilder {
    fun build(): AppDatabase?
    // TODO: only for test, need realize custom migration without recreation db
    fun migrate()
}