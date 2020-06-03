package com.soulkey.fspicker.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.soulkey.fspicker.R
import com.soulkey.fspicker.config.Constant.PERMISSION_REQUEST_CODE
import com.soulkey.fspicker.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var venueAdapter: VenueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel
        mainViewModel.clearVenues()

        // 위치 권한 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }

        // GMS 클라이언트
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //Venue List 연결
        venueAdapter = VenueAdapter()
        recycler_venue_list.apply { adapter = venueAdapter }
        mainViewModel.recommendedVenues.observe(this, Observer {
            venueAdapter.submitList(it)
            pb_venues_loading.visibility = View.GONE
            if (it.isNotEmpty()) {
                tv_guide_to_refresh.visibility = View.GONE
                recycler_venue_list.visibility = View.VISIBLE
            } else if (it.isEmpty() && mainViewModel.isVenuesLoaded.value == true) {
                tv_guide_to_refresh.visibility = View.VISIBLE
                tv_guide_to_refresh.text = "Can't Find Any Venue.."
            } else {
                tv_guide_to_refresh.visibility = View.VISIBLE
                tv_guide_to_refresh.text = "Allow the Location Setting \n&\n Press Refresh Button"
            }
        })

        //FAB 동작 설정
        fab_refresh.setOnClickListener {
            pb_venues_loading.visibility = View.VISIBLE
            tv_guide_to_refresh.visibility = View.GONE
            recycler_venue_list.visibility = View.GONE
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Timber.v("diver:/ LL updated")
                    mainViewModel.updateCurrentLL(it.latitude, it.longitude)
                }
            }.addOnFailureListener {
                mainViewModel.updateCurrentLL(1.283644,103.860753)
                Timber.v("diver:/ ${it.localizedMessage}")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.clearVenues()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Timber.v("diver:/ Granted")
                } else {
                    Toast.makeText(this, "Permission denied, Set as the Default Location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}