package com.example.mappaddingactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.mappaddingactivity.AnchorSheetBehavior.AnchorSheetCallback
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.roundToInt


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var bsBehavior: AnchorSheetBehavior<View>
    var tapactionlayout: Button? = null
    var backBtn: FloatingActionButton? = null
    private lateinit var bottomSheet: View
    private lateinit var mMap: GoogleMap
    val sydney = LatLng(-34.0, 151.0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        tapactionlayout = findViewById(R.id.tap_action_layout)
        backBtn = findViewById(R.id.backBtn)
        bottomSheet = findViewById(R.id.bottom_map)
        setUpAnchor(bottomSheet)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
    private fun setUpAnchor(btm: View){
        bsBehavior = AnchorSheetBehavior.from(btm)
        bsBehavior.state = AnchorSheetBehavior.STATE_COLLAPSED
        bsBehavior.setAnchorOffset(0.5f)
        bsBehavior.setAnchorSheetCallback(object : AnchorSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == AnchorSheetBehavior.STATE_COLLAPSED) {
                    //action if needed
                }
                if (newState == AnchorSheetBehavior.STATE_EXPANDED) {
                }
                if (newState == AnchorSheetBehavior.STATE_DRAGGING) {

                }
                if (newState == AnchorSheetBehavior.STATE_ANCHOR) {
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                val h = bottomSheet.height.toFloat()
                val off = h * slideOffset
                when (bsBehavior.getState()) {
                    AnchorSheetBehavior.STATE_DRAGGING -> {
                        setMapPaddingBotttom(off)


                        //reposition marker at the center
                        if (sydney != null) mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    }
                    AnchorSheetBehavior.STATE_SETTLING -> {
                        setMapPaddingBotttom(off)
                        //reposition marker at the center
                        if (sydney != null) mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    }
                    AnchorSheetBehavior.STATE_HIDDEN -> {
                    }
                    AnchorSheetBehavior.STATE_EXPANDED -> {
                    }
                    AnchorSheetBehavior.STATE_COLLAPSED -> {
                    }
                }
            }
        })

        tapactionlayout!!.setOnClickListener {
            if (bsBehavior.state == AnchorSheetBehavior.STATE_COLLAPSED) {
                bsBehavior.state = AnchorSheetBehavior.STATE_ANCHOR
            }
        }
        backBtn!!.setOnClickListener {
            if (bsBehavior.state == AnchorSheetBehavior.STATE_ANCHOR) {
                bsBehavior.state = AnchorSheetBehavior.STATE_COLLAPSED
            }
        }

    }
    fun setMapPaddingBotttom(offset: Float) {
        val maxMapPaddingBottom = 1.0f
        mMap.setPadding(0, 0, 0, (offset * maxMapPaddingBottom).roundToInt())
    }

}