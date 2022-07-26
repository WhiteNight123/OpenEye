package com.example.openeye.logic.room

import android.content.Context
import androidx.room.*
import com.google.gson.annotations.SerializedName
import io.reactivex.rxjava3.core.Single
import java.io.Serializable

/**
 * 观看历史的room
 */
@Database(version = 1, entities = [HistoryWatchEntity::class])
abstract class HistoryWatchDatabase : RoomDatabase() {
    abstract fun historyWatchDao(): HistoryWatchDao

    companion object {
        private var instance: HistoryWatchDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): HistoryWatchDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(
                context.applicationContext,
                HistoryWatchDatabase::class.java, "watch_history_database"
            )
                .build().apply {
                    instance = this
                }
        }

    }
}

@Entity
data class HistoryWatchEntity(
    @SerializedName("videoTitle")
    val videoTitle: String?,
    @SerializedName("videoUrl")
    val videoUrl: String?,
    @SerializedName("videoId")
    val videoId: Int,
    @SerializedName("videoDescription")
    val videoDescription: String?,
    @SerializedName("likeCount")
    var likeCount: Int?,
    @SerializedName("shareCount")
    val shareCount: Int?,
    @SerializedName("replyCount")
    val replyCount: Int?,
    @SerializedName("authorIcon")
    val authorIcon: String?,
    @SerializedName("authorName")
    val authorName: String?,
    @SerializedName("authorDescription")
    val authorDescription: String?,
    @SerializedName("videoCover")
    val videoCover: String?,
    @SerializedName("videoDuration")
    val videoDuration: String?,
    @SerializedName("nextPageUrl")
    var nextPageUrl: String?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}

@Dao
interface HistoryWatchDao {
    // 获取全部数据
    @Query("SELECT * FROM historywatchentity")
    fun getAll(): Single<List<HistoryWatchEntity>>

    // 插入数据
    @Insert
    fun insert(history: HistoryWatchEntity): Single<Long>
    // 删库
    @Query("DELETE FROM historywatchentity")
    fun deleteAll(): Single<Int>
    // 删除某一条数据
    @Delete
    fun delete(history: HistoryWatchEntity): Single<Unit>

    // 根据videoId查询
    @Query("SELECT * FROM historywatchentity WHERE videoId = :videoId")
    fun findHistoryWatchVideo(videoId: Int): Single<List<HistoryWatchEntity>>

    // 更新某一条数据
    @Update()
    fun update(history: HistoryWatchEntity): Single<Unit>

}