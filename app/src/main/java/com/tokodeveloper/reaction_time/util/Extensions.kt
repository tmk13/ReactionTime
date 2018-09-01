package com.tokodeveloper.reaction_time.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
        provider: ViewModelProvider.Factory) =
        ViewModelProviders.of(this, provider).get(VM::class.java)