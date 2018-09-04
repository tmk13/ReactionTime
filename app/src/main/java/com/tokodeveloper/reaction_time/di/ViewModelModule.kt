package com.tokodeveloper.reaction_time.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tokodeveloper.reaction_time.viewmodels.GameViewModel
import com.tokodeveloper.reaction_time.viewmodels.HistoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(viewModel: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HistoryViewModel::class)
    abstract fun bindHistoryViewModel(viewModel: HistoryViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}