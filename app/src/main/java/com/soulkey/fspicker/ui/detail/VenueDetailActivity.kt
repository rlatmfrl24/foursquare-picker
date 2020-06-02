package com.soulkey.fspicker.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.soulkey.fspicker.R
import com.soulkey.fspicker.databinding.ActivityVenueDetailBinding
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.activity_venue_detail.tv_venue_name
import kotlinx.android.synthetic.main.item_venue.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class VenueDetailActivity : AppCompatActivity() {
    private val venueDetailViewModel: VenueDetailViewModel by viewModel()
    private lateinit var binding: ActivityVenueDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_venue_detail)
        binding.lifecycleOwner = this
        binding.viewModel = venueDetailViewModel

        val venueName = intent.getStringExtra("venueName")!!
        val venueAddress = intent.getStringExtra("venueAddress")!!
        val requestId = intent.getStringExtra("fsId")!!
        venueDetailViewModel.setBasicData(venueName, venueAddress)
        venueDetailViewModel.requestVenueDetail(requestId)
    }
}