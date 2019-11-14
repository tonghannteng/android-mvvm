package com.tengtonghann.android.androidmvvm.data.network.response

import com.tengtonghann.android.androidmvvm.data.db.entities.Quote

data class QuoteResponse(
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)