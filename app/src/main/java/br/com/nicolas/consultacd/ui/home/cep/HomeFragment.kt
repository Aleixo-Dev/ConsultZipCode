package br.com.nicolas.consultacd.ui.home.cep

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.nicolas.consultacd.R
import br.com.nicolas.consultacd.databinding.FragmentHomeBinding
import br.com.nicolas.consultacd.domain.DirectModel
import br.com.nicolas.consultacd.models.CepRemote
import br.com.nicolas.consultacd.ui.home.adapter.HomeAdapter
import br.com.nicolas.consultacd.ui.map.MapFragment
import br.com.nicolas.consultacd.utils.showSnack
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private lateinit var myAdView: AdView
    private var mInterstitialAd: InterstitialAd? = null
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
        setupView()
    }

    /* When the request succeeds, display the ad on the screen */
    private fun render() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.Error, HomeState.CepInvalid -> {
                    setupVisibilities(contentError = true)
                }

                HomeState.Loading -> setupVisibilities(progressBar = true)
                is HomeState.Success -> {
                    binding.run {
                        setupViewCep(state.data)
                        showAd()
                    }
                }

                is HomeState.SuccessDirect -> {
                    setupRecyclerViewDirect(state.directModel)
                    showAd()
                }
            }
        }
    }

    private fun setupRecyclerViewDirect(directModel: DirectModel) {
        with(binding.recyclerViewDdd) {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = directModel.cities?.let { HomeAdapter(it, directModel.state.toString()) }
        }
    }

    private fun setupView() = binding.run {
        initAd()
        initAdInterstitial()
        setupVisibilitiesRadio()
        fetchCep()
        setupBugReport()
    }

    private fun FragmentHomeBinding.initAd() {
        myAdView = adView
        val adRequest = AdRequest.Builder().build()
        myAdView.loadAd(adRequest)
    }

    /* Initialize Ad */
    private fun initAdInterstitial() = context?.let { context ->
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            getString(R.string.admob_key_id_test),
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
            mInterstitialAd?.show(requireActivity())
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    initAdInterstitial()
                    binding.textInputLayoutInputCode.editText?.run {
                        clearFocus()
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    super.onAdFailedToShowFullScreenContent(adError)
                    mInterstitialAd = null
                }
            }
        }
    }

    private fun FragmentHomeBinding.setupVisibilitiesRadio() {
        radioGroupCepAndDdd.apply {
            setOnCheckedChangeListener { _, radioClicked ->
                when (radioClicked) {
                    radioButtonCep.id -> {
                        setChecked(isCepChecked = true)
                        cleanTextInput()
                    }

                    radioButtonDdd.id -> {
                        setChecked(isDddChecked = true)
                        cleanTextInput()
                    }
                }
            }
        }
    }

    private fun FragmentHomeBinding.fetchCep() {
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

    private fun cleanTextInput() {
        binding.textInputLayoutInputCode.editText?.text?.clear()
    }

    private fun setChecked(isCepChecked: Boolean = false, isDddChecked: Boolean = false) {
        this.isCepChecked = isCepChecked
        this.isDddChecked = isDddChecked
    }

    private fun FragmentHomeBinding.setupViewCep(cepRemote: CepRemote?) {
        setupVisibilities(layoutCep = true)
        cepRemote?.let { cep ->
            includeCep.apply {
                textViewCepCity.text = getString(R.string.city_with_one_more_char, cep.city)
                textViewCepNeighborhood.text = cep.neighborhood
                textViewCepService.text = getString(R.string.see_on_map)
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

    private fun FragmentHomeBinding.setupBugReport() {
        imageViewBugReport.setOnClickListener {
            startActivity(
                Intent(
                    ACTION_VIEW,
                    Uri.parse(getString(R.string.web_form_bug_report))
                )
            )
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
        private const val TAG = "HomeFragment"
        private const val TAG_MAP = "MapFragment"
    }
}