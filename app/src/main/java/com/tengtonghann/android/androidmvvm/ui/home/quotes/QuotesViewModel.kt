package com.tengtonghann.android.androidmvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.tengtonghann.android.androidmvvm.data.repositories.QuoteRepository
import com.tengtonghann.android.androidmvvm.util.lazyDeferred

class QuotesViewModel(
    repository: QuoteRepository
) : ViewModel() {
    val quotes by lazyDeferred {
        repository.getQuotes()
    }

}
