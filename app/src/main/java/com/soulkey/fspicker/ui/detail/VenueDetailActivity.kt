package com.soulkey.fspicker.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soulkey.fspicker.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class VenueDetailActivity : AppCompatActivity() {
    private val venueDetailViewModel: VenueDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_detail)
        val requestId = intent.getStringExtra("fsId")!!
//        venueDetailViewModel.requestVenueDetail(requestId)
    }
}