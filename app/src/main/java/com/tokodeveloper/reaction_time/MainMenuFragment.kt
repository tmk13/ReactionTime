package com.tokodeveloper.reaction_time

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.games.Games
import com.google.android.material.snackbar.Snackbar
import com.tokodeveloper.reaction_time.databinding.FragmentMainMenuBinding
import com.tokodeveloper.reaction_time.viewmodels.MainMenuViewModel
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenuFragment : Fragment() {

    private lateinit var signedInAccount: GoogleSignInAccount

    private lateinit var menu: Menu
    private lateinit var snackbar: Snackbar

    companion object {
        private const val RC_SIGN_IN = 9001
        private const val REQUEST_LEADERBOARDS = 8000
        private const val REQUEST_ACHIEVEMENTS = 8001
        private const val GOOGLE_API_ERROR = 10001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSignInIntent()
    }

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
                this@MainMenuFragment.requireActivity().findNavController(R.id.startGame).navigate(direction)
            }
        })
        mainMenuViewModel.leaderboard.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                leaderboards()
            }
        })
        mainMenuViewModel.achievements.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                achievements()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isSignedIn()) {
            signInButton.visibility = View.GONE
        } else {
            signInButton.visibility = View.VISIBLE
        }

        signInButton.setOnClickListener { startSignInIntent() }
        configureSnackbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main_menu, menu)
        this.menu = menu!!

        if (isSignedIn()) {
            setSingOutMenuVisibility(true)
        } else {
            setSingOutMenuVisibility(false)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.signOut -> {
                signOut()
                return true
            }
            R.id.history -> {
                val direction = MainMenuFragmentDirections.ActionMainMenuFragmentToHistoryFragment()
                findNavController().navigate(direction)
                return true
            }
            R.id.consents -> {
                // TODO
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSignInIntent() {
        val signInClient = GoogleSignIn.getClient(requireActivity(),
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
        val intent = signInClient.signInIntent
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(request: Int, response: Int, data: Intent?) {
        super.onActivityResult(request, response, data)

        if (request == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                signedInAccount = task.getResult(ApiException::class.java)
                signInButton.visibility = View.GONE
                setSingOutMenuVisibility(true)
            } catch (apiException: ApiException) {
                GoogleApiAvailability.getInstance().getErrorDialog(requireActivity(), apiException.statusCode, GOOGLE_API_ERROR).show()
            }
        }
    }

    fun leaderboards() {
        if (isSignedIn()) {
            Games.getLeaderboardsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                    .getLeaderboardIntent(getString(R.string.leaderboard_average_of_5))
                    .addOnSuccessListener { intent ->
                        if (intent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivityForResult(intent, REQUEST_LEADERBOARDS)
                        }
                    }
        } else {
            showNotConnectedSnackbar()
        }
    }

    fun achievements() {
        if (isSignedIn()) {
            Games.getAchievementsClient(requireActivity(), GoogleSignIn.getLastSignedInAccount(requireActivity())!!)
                    .achievementsIntent
                    .addOnSuccessListener { intent ->
                        if (intent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivityForResult(intent, REQUEST_ACHIEVEMENTS)
                        }
                    }
        } else {
            showNotConnectedSnackbar()
        }
    }

    private fun showNotConnectedSnackbar() {
        snackbar.show()
    }

    private fun isSignedIn(): Boolean {
        return GoogleSignIn.getLastSignedInAccount(requireActivity()) != null
    }

    private fun signOut() {
        if (isSignedIn()) {
            val signInClient = GoogleSignIn.getClient(requireActivity(),
                    GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)

            signInClient.signOut().addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    signInButton.visibility = View.VISIBLE
                    setSingOutMenuVisibility(false)
                } else {

                }
            }
        } else {
            showNotConnectedSnackbar()
        }
    }

    private fun setSingOutMenuVisibility(visible: Boolean) {
        menu.findItem(R.id.signOut).isVisible = visible
    }

    private fun configureSnackbar() {
        snackbar = Snackbar.make(mainLayout, R.string.notConnected, Snackbar.LENGTH_LONG)
        val snackbarView = snackbar.view
        val textView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.isAllCaps = true
        textView.textSize = 20f
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red))
        snackbarView.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.white))
    }
}
