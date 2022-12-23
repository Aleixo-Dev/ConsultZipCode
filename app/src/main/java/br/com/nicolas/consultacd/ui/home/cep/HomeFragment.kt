package br.com.nicolas.consultacd.ui.home.cep

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import br.com.nicolas.consultacd.R
import br.com.nicolas.consultacd.databinding.FragmentHomeBinding
import br.com.nicolas.consultacd.models.CepRemote
import br.com.nicolas.consultacd.ui.home.adapter.HomeAdapter
import br.com.nicolas.consultacd.ui.map.MapFragment
import br.com.nicolas.consultacd.utils.hideKeyboard
import br.com.nicolas.consultacd.utils.showSnack
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var adView: AdView
    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "MainActivity"
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var isCepChecked = true
    private var isDddChecked = false
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        render()
        fetchCep()
        setupVisibilitiesRadio()
        initAdInterstitial()
    }

    private fun initAd() {
        adView = binding.adView
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    /* initialize ad */
    private fun initAdInterstitial() = context?.let {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            it,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    initAdInterstitial()
                }
                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    mInterstitialAd = null
                }
            }
            mInterstitialAd?.show(requireActivity())
        }
    }

    /* when the request succeeds, display the ad on the screen */
    private fun render() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.Error, HomeState.CepInvalid -> setupVisibilities(contentError = true)
                HomeState.Loading -> setupVisibilities(progressBar = true)
                is HomeState.Success -> {
                    binding.apply {
                        setupViewCep(state.data)
                        binding.textInputLayoutInputCode.hideKeyboard()
                        showAd()
                    }
                }
                is HomeState.SuccessDirect -> {
                    setupRecyclerViewDirect(state.cities ?: emptyList())
                    binding.textInputLayoutInputCode.hideKeyboard()
                    showAd()
                }
            }
        }
    }

    private fun setupRecyclerViewDirect(cities: List<String>) {
        with(binding.recyclerViewDdd) {
            adapter = HomeAdapter(cities)
            setHasFixedSize(true)
        }
    }

    private fun fetchCep() = binding.apply {
        textInputLayoutInputCode.editText?.addTextChangedListener {
            val countInput = it.toString().length
            when {
                isCepChecked && countInput == 8 -> {
                    setHomeEvent(HomeEvent.OnFetchCep(it.toString()))
                }
                isDddChecked && countInput == 2 -> {
                    setHomeEvent(HomeEvent.OnFetchDirect(it.toString()))
                    setupVisibilities(recyclerDdd = true)
                }
                else -> setHomeEvent(HomeEvent.InvalidCode)
            }
        }
    }

    private fun setupVisibilitiesRadio() {
        binding.radioGroupCepAndDdd.apply {
            setOnCheckedChangeListener { _, radioClicked ->
                when (radioClicked) {
                    binding.radioButtonCep.id -> {
                        setChecked(isCepChecked = true)
                        cleanTextInput()
                    }
                    binding.radioButtonDdd.id -> {
                        setChecked(isDddChecked = true)
                        cleanTextInput()
                    }
                }
            }
        }
    }

    private fun cleanTextInput() {
        binding.textInputLayoutInputCode.editText?.text?.clear()
    }

    private fun setChecked(isCepChecked: Boolean = false, isDddChecked: Boolean = false) {
        this.isCepChecked = isCepChecked
        this.isDddChecked = isDddChecked
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentHomeBinding.setupViewCep(cepRemote: CepRemote?) {
        setupVisibilities(layoutCep = true)
        cepRemote?.let { cep ->
            includeCep.apply {
                textViewCepCity.text = getString(R.string.city_with_one_more_char, cep.city)
                textViewCepNeighborhood.text = cep.neighborhood
                textViewCepService.text = getString(
                    R.string.service_with_one_more_char, setupNameService(cepRemote.service)
                )
                textViewCepState.text = cep.state
                textViewCepStreet.text = cep.street
                setupClickView(cep)
            }
        }
    }

    private fun setupClickView(cepRemote: CepRemote) = binding.apply {
        includeCep.viewClick.setOnClickListener {
            if (cepRemote.location.coordinates?.latitude != null) {
                MapFragment.build(cepRemote).show(childFragmentManager, TAG_MAP)
            } else {
                binding.root.showSnack(getString(R.string.snack_error_map))
            }
        }
    }

    private fun setupNameService(service: String): String {
        return when (service) {
            getString(R.string.symbol_service_alt) -> {
                service.split(DELIMITER)[0]
            }
            getString(R.string.symbol_service) -> {
                service
            }
            else -> {
                getString(R.string.nothing_found)
            }
        }
    }

    private fun setupVisibilities(
        layoutCep: Boolean = false,
        recyclerDdd: Boolean = false,
        contentError: Boolean = false,
        progressBar: Boolean = false
    ) {
        with(binding) {
            includeCep.root.isVisible = layoutCep
            recyclerViewDdd.isVisible = recyclerDdd
            contentNotFound.root.isVisible = contentError
            progressBarLoadingCall.isVisible = progressBar
        }
    }

    private fun setHomeEvent(newEvent: HomeEvent) {
        viewModel.interactCep(newEvent)
    }

    companion object {
        private const val DELIMITER = "-"
        private const val TAG_MAP = "MapFragment"
    }
}