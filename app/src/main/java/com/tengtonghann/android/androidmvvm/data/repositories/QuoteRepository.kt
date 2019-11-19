package com.tengtonghann.android.androidmvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tengtonghann.android.androidmvvm.data.db.AppDatabase
import com.tengtonghann.android.androidmvvm.data.db.entities.Quote
import com.tengtonghann.android.androidmvvm.data.network.MyApi
import com.tengtonghann.android.androidmvvm.data.network.SafeApiRequest
import com.tengtonghann.android.androidmvvm.data.preferences.PreferenceProvider
import com.tengtonghann.android.androidmvvm.util.Coroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private const val MINIMUM_INTERVAL = 6

class QuoteRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
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
        val lastSaveAt = prefs.getLastSaveAt()
        if (lastSaveAt == null || isFetchNeeded(LocalDateTime.parse(lastSaveAt))) {
            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(saveAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(saveAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutine.io {
            prefs.saveLastSaveAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }

}