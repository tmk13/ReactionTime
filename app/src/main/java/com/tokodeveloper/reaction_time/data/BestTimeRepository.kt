/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tokodeveloper.reaction_time.data

import org.joda.time.DateTime
import javax.inject.Inject

/**
 * Repository module for handling data operations.
 */
class BestTimeRepository @Inject constructor(private val bestTimeDao: BestTimeDao) {

    fun getBestTimes() = bestTimeDao.getBestTimes()

    fun getBestTime(date: DateTime) = bestTimeDao.getBestTime(date)

    fun saveBestTime(bestTime: BestTime) = bestTimeDao.insertBestTime(bestTime)

    fun updateBestTime(bestTime: BestTime) = bestTimeDao.updateBestTime(bestTime)
}
