package com.tokodeveloper.reaction_time

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.runner.AndroidJUnit4
import com.tokodeveloper.reaction_time.data.AppDatabase
import com.tokodeveloper.reaction_time.data.BestTime
import com.tokodeveloper.reaction_time.data.BestTimeDao
import org.hamcrest.CoreMatchers
import org.joda.time.DateTime
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InsertAndRetrieveWithRoomTest {
    private lateinit var bestTimeDao: BestTimeDao
    private lateinit var appDatabase: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        bestTimeDao = appDatabase.bestTimeDao()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun writeBestTimeAndReadNotNull() {
        val bestTime = BestTime(bestTimeId = 1, bestTime = 999)
        bestTimeDao.insertBestTime(bestTime)
        val todayTime = bestTimeDao.getBestTime(DateTime.now().withTimeAtStartOfDay())
        ViewMatchers.assertThat(todayTime, CoreMatchers.equalTo(bestTime))
    }
}