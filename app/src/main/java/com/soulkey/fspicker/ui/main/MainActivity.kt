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
import com.soulkey.fspicker.lib.common.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : BaseActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var venueAdapter: VenueAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel
        mainViewModel.loadRecentLocationName()

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
            Timber.v("diver: ${it.size}")
            if (it.isEmpty()) {
                tv_guide_to_refresh.visibility = View.VISIBLE
                recycler_venue_list.visibility = View.INVISIBLE
            } else {
                tv_guide_to_refresh.visibility = View.INVISIBLE
                recycler_venue_list.visibility = View.VISIBLE
            }
        })

        mainViewModel.isVenuesLoading.observe(this, Observer {loading->
            if (loading){
                pb_venues_loading.visibility = View.VISIBLE
                layout_venue_container.visibility = View.GONE
            } else {
                pb_venues_loading.visibility = View.GONE
                layout_venue_container.visibility = View.VISIBLE
            }
        })

        //FAB 동작 설정
        fab_refresh.setOnClickListener {
            mainViewModel.isVenuesLoading.value = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Timber.v("diver:/ LL updated")
                    mainViewModel.updateCurrentLL(it.latitude, it.longitude)
                }
            }.addOnFailureListener {
                Timber.v("diver:/ ${it.localizedMessage}")
                mainViewModel.getRecentLL().also { LL->
                    mainViewModel.updateCurrentLL(LL.first.toDouble(), LL.second.toDouble())
                }
                Timber.v("diver:/ ${it.localizedMessage}")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.getRecentLocationData()
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