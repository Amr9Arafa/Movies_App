package com.example.moviesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: MoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        adapter = MoviesAdapter(MoviesAdapter.MovieListener {
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToMovieDetailFragment(it))

            Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show()
        })
        binding.moviesRecycler.adapter = adapter
        binding.viewModel = viewModel

        viewModel.topRatedMoviesList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(it)
            }
        }



        return binding.root
    }


}