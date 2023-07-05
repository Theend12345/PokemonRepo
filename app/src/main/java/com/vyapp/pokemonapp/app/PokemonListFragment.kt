package com.vyapp.pokemonapp.app

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vyapp.pokemonapp.R
import com.vyapp.pokemonapp.databinding.FragmentPokemonListBinding
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonResultDomain
import kotlinx.coroutines.launch
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject
    lateinit var pokemonViewModelFactory: PViewModelFactory

    private val binding: FragmentPokemonListBinding by lazy {
        FragmentPokemonListBinding.inflate(layoutInflater)
    }

    private val viewModel: PokemonViewModel by lazy {
        ViewModelProvider(requireActivity(), pokemonViewModelFactory)[PokemonViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.pokemonListInject(this@PokemonListFragment)
        viewModel.fetchPokemonRemoteList()
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

        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pokemonRemoteList.collect{
                    when (it) {
                        is UIState.Loading -> {

                        }
                        is UIState.Success<PokemonResultDomain> -> {
                            binding.rv.adapter = ItemAdapter(it.data.results)
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

    private fun toPokemonFragment(name: String){
        val nameBundle: Bundle = bundleOf("pokemonName" to name)
        findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonFragment, nameBundle)
    }

    private inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)

        fun bind(item: PokemonDomain) {
            name.text = item.name.uppercase()
        }
    }

    private inner class ItemAdapter(val items: List<PokemonDomain>) :
        RecyclerView.Adapter<UserViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val view = layoutInflater.inflate(R.layout.item_list, parent, false)
            return UserViewHolder(view)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)

            holder.itemView.setOnClickListener {
                toPokemonFragment(item.name)
            }
        }

        override fun getItemCount() = items.size

    }


}