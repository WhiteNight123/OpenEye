package com.example.openeye.logic.room

import android.content.Context
import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Database(version = 1, entities = [HistoryEntity::class])
abstract class HistorySearchDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        private var instance: HistorySearchDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): HistorySearchDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                HistorySearchDatabase::class.java, "search_history_database"
            )
                .build().apply {
                    instance = this
                }
        }
    }
}

@Entity
data class HistoryEntity(var key: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface HistoryDao {
    @Query("SELECT * FROM historyentity")
    fun getAll(): Single<List<HistoryEntity>>

    @Insert
    fun insert(history: HistoryEntity): Single<Long>

    @Query("DELETE FROM historyentity")
    fun delete(): Single<Int>
}
