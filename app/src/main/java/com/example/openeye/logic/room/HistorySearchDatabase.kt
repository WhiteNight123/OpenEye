package com.example.openeye.logic.room

import android.content.Context
import androidx.room.*
import io.reactivex.rxjava3.core.Single
/**
 * 搜索历史的Room
 **/
@Database(version = 1, entities = [HistorySearchEntity::class])
abstract class HistorySearchDatabase : RoomDatabase() {
    abstract fun historySearchDao(): HistorySearchDao

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
data class HistorySearchEntity(var key: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface HistorySearchDao {
    // 获取所以
    @Query("SELECT * FROM historysearchentity")
    fun getAll(): Single<List<HistorySearchEntity>>

    // 插入一条
    @Insert
    fun insert(history: HistorySearchEntity): Single<Long>

    // 删库😜
    @Query("DELETE FROM historysearchentity")
    fun delete(): Single<Int>
}
