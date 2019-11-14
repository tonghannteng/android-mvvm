package com.tengtonghann.android.androidmvvm

import android.app.Application
import com.tengtonghann.android.androidmvvm.data.db.AppDatabase
import com.tengtonghann.android.androidmvvm.data.network.MyApi
import com.tengtonghann.android.androidmvvm.data.network.NetworkConnectionInterceptor
import com.tengtonghann.android.androidmvvm.data.repositories.QuoteRepository
import com.tengtonghann.android.androidmvvm.data.repositories.UserRepository
import com.tengtonghann.android.androidmvvm.ui.auth.AuthViewModelFactory
import com.tengtonghann.android.androidmvvm.ui.home.profile.ProfileViewModelFactory
import com.tengtonghann.android.androidmvvm.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MVVMApplication : Application(), KodeinAware {

    override val kodein = Kodein.lazy {

        import(androidXModule(this@MVVMApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from singleton { QuoteRepository(instance(), instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }

    }
}