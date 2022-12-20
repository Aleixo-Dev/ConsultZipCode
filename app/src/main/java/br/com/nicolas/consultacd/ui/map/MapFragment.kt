package br.com.nicolas.consultacd.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.nicolas.consultacd.R
import br.com.nicolas.consultacd.databinding.FragmentMapBinding
import br.com.nicolas.consultacd.models.CepRemote
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapFragment : BottomSheetDialogFragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    var cep: CepRemote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        map!!.getMapAsync(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            setupView()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val location = cep?.location?.coordinates?.let {
            LatLng(it.latitude!!.toDouble(), it.longitude!!.toDouble())
        } ?: LatLng(0.0, 0.0)
        map.addMarker(
            MarkerOptions()
                .position(location)
                .title(cep?.street.toString())
        )
        map.moveCamera(CameraUpdateFactory.newLatLng(location))
        map.animateCamera(CameraUpdateFactory.zoomTo(9.0f))
        map.isBuildingsEnabled = true
    }

    private fun FragmentMapBinding.setupView() {
        cep?.let {
            textViewMapCepCity.text = it.city
            textViewMapCepNeighborhood.text = it.neighborhood
            textViewMapCepStreet.text = it.street
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun build(cep: CepRemote) = MapFragment().apply {
            this.cep = cep
        }
    }
}