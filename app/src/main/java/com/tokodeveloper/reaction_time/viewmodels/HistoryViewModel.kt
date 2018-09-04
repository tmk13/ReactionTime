package com.tokodeveloper.reaction_time.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tokodeveloper.reaction_time.data.BestTime
import com.tokodeveloper.reaction_time.data.BestTimeRepository
import javax.inject.Inject

class HistoryViewModel @Inject constructor(bestTimeRepository: BestTimeRepository) : ViewModel() {

    val getBestTimes: LiveData<List<BestTime>> = bestTimeRepository.getBestTimes()
}