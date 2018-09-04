package com.tokodeveloper.reaction_time.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "best_times")
data class BestTime(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var bestTimeId: Long = 0,
        val date: DateTime = DateTime.now().withTimeAtStartOfDay(),
        val bestTime: Long
)