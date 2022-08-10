package br.com.nicolas.consultacd.ui.home.cep

import android.annotation.SuppressLint
import android.os.Bundle
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var isCepChecked = true
    private var isDddChecked = false

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        render()
        fetchCep()
        setupVisibilitiesRadio()
    }

    private fun render() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is HomeState.Error, HomeState.CepInvalid -> setupVisibilities(contentError = true)
                HomeState.Loading -> setupVisibilities(progressBar = true)
                is HomeState.Success -> setupViewCep(state.data)
                is HomeState.SuccessDirect -> setupRecyclerViewDirect(state.cities ?: emptyList())
            }
        }
    }

    private fun setupRecyclerViewDirect(cities : List<String>) {
        with(binding.recyclerViewDdd){
            adapter = HomeAdapter(cities)
            setHasFixedSize(true)
        }
    }

    private fun fetchCep() = binding.apply {
        textInputLayoutInputCode.editText?.addTextChangedListener {
            val countInput = it.toString().length
            when {
                isCepChecked && countInput == 8 -> setHomeEvent(HomeEvent.OnFetchCep(it.toString()))
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
    private fun setupViewCep(cepRemote: CepRemote?) {
        setupVisibilities(layoutCep = true)
        cepRemote?.let {
            val isCorreios = it.service == "correios"
            binding.includeCep.apply {
                textViewCepCity.text = getString(R.string.city_with_one_more_char, it.city)
                textViewCepNeighborhood.text = it.neighborhood
                textViewCepService.text = if (isCorreios) getString(
                    R.string.service_with_one_more_char,
                    it.service.replaceFirstChar { "C" }) else ""
                textViewCepState.text = it.state
                textViewCepStreet.text = it.street
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
}