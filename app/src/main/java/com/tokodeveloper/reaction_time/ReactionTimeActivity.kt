package com.tokodeveloper.reaction_time

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tokodeveloper.reaction_time.data.BestTime


class ReactionTimeActivity : AppCompatActivity(), HistoryFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reaction_time)
    }

    override fun onListFragmentInteraction(bestTime: BestTime?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
