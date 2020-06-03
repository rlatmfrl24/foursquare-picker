package com.soulkey.fspicker.lib.common

import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

open class BaseActivity : AppCompatActivity(){
    private val disposables = CompositeDisposable()

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> onBackPressed().run { true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        Timber.d("onStart ${toString()}")
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        Timber.d("onResume ${toString()}")
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        Timber.d("onPause ${toString()}")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop ${toString()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy ${toString()}")
        disposables.clear()
    }
}