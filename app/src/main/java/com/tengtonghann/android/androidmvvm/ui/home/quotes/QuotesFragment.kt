package com.tengtonghann.android.androidmvvm.ui.home.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.tengtonghann.android.androidmvvm.R
import com.tengtonghann.android.androidmvvm.data.db.entities.Quote
import com.tengtonghann.android.androidmvvm.util.Coroutine
import com.tengtonghann.android.androidmvvm.util.hide
import com.tengtonghann.android.androidmvvm.util.show
import com.tengtonghann.android.androidmvvm.util.toast
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.quotes_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuotesFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: QuotesViewModel
    private val factory: QuotesViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quotes_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(QuotesViewModel::class.java)
        bindUI()

//        Coroutine.main {
//            val quotes = viewModel.quotes.await()
//            quotes.observe(this, Observer {
//                context?.toast(it.size.toString())
//            })
//        }
    }

    private fun bindUI() = Coroutine.main {
        progress_bar.show()
        viewModel.quotes.await().observe(this, Observer {
            progress_bar.hide()
            initRecylerview(it.toQuoteItem())
        })
    }

    private fun initRecylerview(toQuoteItem: List<QuoteItem>) {

        val mAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(toQuoteItem)
        }

        recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }

    }

    private fun List<Quote>.toQuoteItem(): List<QuoteItem> {
        return this.map {
            QuoteItem(it)
        }
    }
}
