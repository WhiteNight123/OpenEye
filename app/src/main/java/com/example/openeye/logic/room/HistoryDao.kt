package com.example.openeye.logic.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface HistoryDao {
    @Query("SELECT * FROM historyentity")
    fun getAll(): Single<List<HistoryEntity>>

    @Insert
    fun insert(history: HistoryEntity): Single<Long>

    @Query("DELETE FROM historyentity")
    fun delete(): Single<Int>
}