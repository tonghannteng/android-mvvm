package com.tengtonghann.android.androidmvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tengtonghann.android.androidmvvm.data.db.AppDatabase
import com.tengtonghann.android.androidmvvm.data.db.entities.Quote
import com.tengtonghann.android.androidmvvm.data.network.MyApi
import com.tengtonghann.android.androidmvvm.data.network.SafeApiRequest
import com.tengtonghann.android.androidmvvm.util.Coroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuoteRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    private val quotes = MutableLiveData<List<Quote>>()

    init {
        quotes.observeForever { saveQuotes(it) }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            db.getQuoteDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {
        if (isFetchNeeded()) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutine.io {
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}