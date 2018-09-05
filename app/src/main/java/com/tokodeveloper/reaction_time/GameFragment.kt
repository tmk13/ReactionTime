package com.tokodeveloper.reaction_time

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.games.Games
import com.tokodeveloper.reaction_time.data.BestTime
import com.tokodeveloper.reaction_time.data.BestTimeRepository
import com.tokodeveloper.reaction_time.databinding.FragmentGameBinding
import com.tokodeveloper.reaction_time.util.viewModelProvider
import com.tokodeveloper.reaction_time.viewmodels.GameViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.joda.time.DateTime
import javax.inject.Inject


class GameFragment : Fragment() {

    @Inject
    lateinit var bestTimeRepository: BestTimeRepository

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var gameViewModel: GameViewModel

    companion object {
        private const val TAG = "GameFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        gameViewModel = viewModelProvider(viewModelFactory)
        viewLifecycleOwner.lifecycle.addObserver(gameViewModel)
        val binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            viewModel = gameViewModel
            setLifecycleOwner(this@GameFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel.finished.observe(requireActivity(), Observer {
            if (isAdded) {
                if (it) {
                    averageLabel.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green))
                    averageText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green))
                    gameViewModel.average.value?.toLong()?.let {
                        submitScoreToLeaderboard(it)
                        checkAchievements(it)
                        saveScoreToDatabase(it)
                    }
                } else {
                    Log.d(TAG, "setColor red")
                    averageLabel.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
                    averageText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
                }
            }
        })
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun submitScoreToLeaderboard(score: Long) {
        if (isSignedIn()) {
            val leaderboardsClient = Games.getLeaderboardsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
            leaderboardsClient?.submitScore(getString(R.string.leaderboard_average_of_5), score)
        }
    }

    private fun checkAchievements(score: Long) {
        if (isSignedIn()) {
            when {
                score < 500 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_500))
                }
                score < 450 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_450))
                }
                score < 400 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_400))
                }
                score < 375 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_375))
                }
                score < 350 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_350))
                }
                score < 325 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_325))
                }
                score < 300 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_300))
                }
                score < 275 -> {
                    Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                            .unlock(getString(R.string.achievement_below_275))
                }
            }
        }
    }

    private fun saveScoreToDatabase(score: Long) {
        launch(CommonPool) {
            val todayBestTime = bestTimeRepository.getBestTime(DateTime.now().withTimeAtStartOfDay())

            if (todayBestTime != null) {
                if (score < todayBestTime.bestTime) {
                    val newBestTime = todayBestTime.copy(bestTime = score)
                    bestTimeRepository.updateBestTime(newBestTime)
                }
            } else {
                val newBestTime = BestTime(bestTime = score)
                bestTimeRepository.saveBestTime(newBestTime)
            }
        }
    }

    private fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(requireActivity()) != null
    }
}
