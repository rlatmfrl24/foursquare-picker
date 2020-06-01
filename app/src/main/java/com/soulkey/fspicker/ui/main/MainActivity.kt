package com.soulkey.fspicker.ui.main

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.location.*
import com.soulkey.fspicker.R
import com.soulkey.fspicker.config.Constant.PERMISSION_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        mainViewModel.currentLL.observe(this, Observer{
            mainViewModel.callRecommendVenues(it.first, it.second).subscribe { response->
                if (response.isSuccessful) {
                    response.body()?.let {venues->
                        val loc = venues.response["headerLocation"]
                        Timber.v("diver:/ "+loc.asString)
                    }
                }
                else{
                    Toast.makeText(this, "Request Error:: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }
        })

        fab_refresh.setOnClickListener {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    Timber.v("diver:/ LL updated")
                    mainViewModel.updateCurrentLL(it.latitude, it.longitude)
                }
            }
        }
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
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}