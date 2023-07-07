package com.vyapp.pokemonapp.app

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.paging.map
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vyapp.pokemonapp.R
import com.vyapp.pokemonapp.databinding.FragmentPokemonListBinding
import com.vyapp.pokemonapp.domain.model.PokemonDomain
import com.vyapp.pokemonapp.domain.model.PokemonEntityDomain
import com.vyapp.pokemonapp.util.toPokemonDomainEntity
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

    private val adapter: ItemAdapter by lazy {
        ItemAdapter()
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
        binding.rv.adapter = adapter
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.pokemonList.collect {
                    when (it) {
                        is UIState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is UIState.Success<PagingData<PokemonDomain>> -> {
                            binding.progressBar.visibility = View.GONE
                            adapter.submitData(it.data)
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

    //костыль
    private fun toPokemonFragment(name: String) {
        val nameBundle: Bundle = bundleOf("pokemonName" to name)
        findNavController().navigate(R.id.action_pokemonListFragment_to_pokemonFragment, nameBundle)
    }

    private inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.name)

        fun bind(item: PokemonDomain) {
            name.text = item.name.uppercase()
        }
    }

    private object PokemonDiffCallback : DiffUtil.ItemCallback<PokemonDomain>() {
        override fun areItemsTheSame(oldItem: PokemonDomain, newItem: PokemonDomain): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonDomain, newItem: PokemonDomain): Boolean {
            return oldItem.name == newItem.name
        }
    }

    private inner class ItemAdapter :
        PagingDataAdapter<PokemonDomain, PokemonViewHolder>(PokemonDiffCallback) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
            val view = layoutInflater.inflate(R.layout.item_list, parent, false)
            return PokemonViewHolder(view)
        }

        override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
            val item = getItem(position)
            if (item != null) {
                holder.bind(item)
            }

            holder.itemView.setOnClickListener {
                item?.name?.let { name -> toPokemonFragment(name) }
            }
        }
    }

}