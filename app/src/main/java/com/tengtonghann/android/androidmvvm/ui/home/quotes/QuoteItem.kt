package com.tengtonghann.android.androidmvvm.ui.home.quotes

import com.tengtonghann.android.androidmvvm.R
import com.tengtonghann.android.androidmvvm.data.db.entities.Quote
import com.tengtonghann.android.androidmvvm.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem

class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>() {

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}