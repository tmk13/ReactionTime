package com.tokodeveloper.reaction_time.data

import androidx.lifecycle.LiveData
import androidx.room.*
import org.joda.time.DateTime

@Dao
interface BestTimeDao {

    @Query("SELECT * FROM best_times ORDER BY date DESC")
    fun getBestTimes(): LiveData<List<BestTime>>

    @Query("SELECT * FROM best_times WHERE date = :date")
    fun getBestTime(date: DateTime): BestTime?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBestTime(bestTime: BestTime)

    @Update
    fun updateBestTime(bestTime: BestTime)
}