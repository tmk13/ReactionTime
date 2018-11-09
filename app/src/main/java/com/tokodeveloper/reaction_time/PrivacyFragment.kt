package com.tokodeveloper.reaction_time


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tokodeveloper.reaction_time.util.readPrivacyFile
import kotlinx.android.synthetic.main.fragment_privacy.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class PrivacyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_privacy, container, false)

        val privacyFile = readPrivacyFile(requireActivity())
        view.policyWebView.loadDataWithBaseURL(null,
                privacyFile,
                "text/html",
                "utf-8",
                null)

        // Inflate the layout for this fragment
        return view
    }


}
