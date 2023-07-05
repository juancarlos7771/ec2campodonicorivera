package com.juanca_campodonico.ec2_campodonico_rivera.ui.dashboard

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.juanca_campodonico.ec2_campodonico_rivera.R
import com.juanca_campodonico.ec2_campodonico_rivera.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var currentLocationMarker: Marker? = null
    private val DEFAULT_ZOOM = 15f
    private val LOCATION_PERMISSION_REQUEST_CODE = 123

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fcv_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Configura la ubicación actual del usuario si se otorgó permiso
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            enableMyLocation()
        } else {
            requestLocationPermission()
        }

        // Agrega los marcadores y configura la cámara inicial
        val michiCentroLocation = LatLng(-8.377943953035663, -74.52602798465728)
        val mikelaCentroLocation = LatLng(-12.067579370041196, -77.07129597050361)
        val woofwoofCentroLocation = LatLng(-8.084761424423473, -79.00558828924551)
        googleMap.addMarker(MarkerOptions().position(michiCentroLocation).title("Los Michis Store"))
        googleMap.addMarker(MarkerOptions().position(mikelaCentroLocation).title("Veterinaria Mikela"))
        googleMap.addMarker(MarkerOptions().position(woofwoofCentroLocation).title("Veterinaria Woof - Woof"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(michiCentroLocation, DEFAULT_ZOOM))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mikelaCentroLocation, DEFAULT_ZOOM))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(woofwoofCentroLocation, DEFAULT_ZOOM))
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    val latLng = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM))
                }
            }
        }
    }

    private fun requestLocationPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation()
            } else {
                // Manejar la negación del permiso de ubicación
            }
        }
    }
}


