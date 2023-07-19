package com.vyapp.pokemonapp.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.vyapp.pokemonapp.R
import com.vyapp.pokemonapp.databinding.FragmentPokemonBinding
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
import com.vyapp.pokemonapp.util.infoStringFormat
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonFragment : Fragment() {

    @Inject
    lateinit var pokemonViewModelFactory: PViewModelFactory

    private val binding: FragmentPokemonBinding by lazy {
        FragmentPokemonBinding.inflate(layoutInflater)
    }

    private val viewModel: PokemonViewModel by lazy {
        ViewModelProvider(requireActivity(), pokemonViewModelFactory)[PokemonViewModel::class.java]
    }

    override fun onStart() {
        super.onStart()

        binding.backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.pokemonInject(this@PokemonFragment)
        arguments?.getString("pokemonName")?.let { viewModel.fetchPokemon(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemon.collect {
                    when (it) {
                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UIState.Success<PokemonInfoDomain> -> {
                            binding.progressBar.visibility = View.GONE
                            bind(it.data)
                        }
                        is UIState.Error -> {
                            Toast.makeText(requireContext(), it.e.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun bind(data: PokemonInfoDomain) {

        with(binding) {
            pokemonName.text = data.name?.uppercase()
            pokemonHeight.text = data.height?.let {
                infoStringFormat(
                    it,
                    getString(R.string.CM),
                    getString(R.string.height)
                )
            }
            pokemonWeight.text = data.weight?.let {
                infoStringFormat(
                    it,
                    getString(R.string.KG),
                    getString(R.string.weight)
                )
            }
            pokemonType.text =
                data.type?.name?.let { infoStringFormat(it, null, getString(R.string.type)) }
            if (data.sprites != null)
                Glide.with(requireContext()).load(data.sprites.frontDefault).into(pokemonImg)
        }
    }


}