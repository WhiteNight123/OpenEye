package com.example.openeye.logic.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(var key: String?) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}