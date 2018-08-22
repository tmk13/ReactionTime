package com.tokodeveloper.reactiontime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tokodeveloper.reactiontime.databinding.FragmentGameBinding
import com.tokodeveloper.reactiontime.viewmodels.GameViewModel


class GameFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
        val binding = FragmentGameBinding.inflate(inflater, container, false).apply {
            viewModel = gameViewModel
            setLifecycleOwner(this@GameFragment)
        }

        return binding.root
    }

}
