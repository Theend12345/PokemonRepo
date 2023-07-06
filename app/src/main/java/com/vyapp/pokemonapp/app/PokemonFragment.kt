package com.vyapp.pokemonapp.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.vyapp.pokemonapp.databinding.FragmentPokemonBinding
import com.vyapp.pokemonapp.domain.model.PokemonInfoDomain
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.pokemonInject(this@PokemonFragment)
        arguments?.getString("pokemonName")?.let { viewModel.fetchPokemonRemote(it) }
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
                viewModel.pokemonRemote.collect{
                    when (it) {
                        is UIState.Loading -> {

                        }
                        is UIState.Success<PokemonInfoDomain> -> {
                            bind(it.data)
                            Log.d("pokemons", it.data.toString())
                        }
                        is UIState.Error -> {
                            Toast.makeText(requireContext(), it.e.message, Toast.LENGTH_LONG).show()
                            Log.d("pokemons", it.e.message.toString())
                        }

                    }
                }
            }
        }
    }

    private fun bind(data: PokemonInfoDomain){
        with(binding){
            pokemonName.text = data.name
            pokemonHeight.text = data.height.toString()
            pokemonWeight.text = data.weight.toString()
            pokemonType.text = data.type?.name
            Glide.with(requireContext()).load(data.sprites?.frontDefault).into(pokemonImg)
        }
    }


}