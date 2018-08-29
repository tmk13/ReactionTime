package com.tokodeveloper.reaction_time

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.tokodeveloper.reaction_time.databinding.FragmentMainMenuBinding
import com.tokodeveloper.reaction_time.viewmodels.MainMenuViewModel


class MainMenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val mainMenuViewModel = ViewModelProviders.of(this).get(MainMenuViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentMainMenuBinding>(
                layoutInflater, R.layout.fragment_main_menu, container, false).apply { 
            viewModel = mainMenuViewModel
            setLifecycleOwner(this@MainMenuFragment)
        }

        mainMenuViewModel.startGame.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                val direction = MainMenuFragmentDirections.ActionMainMenuFragmentToGameFragment()
                it.findNavController().navigate(direction)
            }
        })
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.consents -> {
                // TODO
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
