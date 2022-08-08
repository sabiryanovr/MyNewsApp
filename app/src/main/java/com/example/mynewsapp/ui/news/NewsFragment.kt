package com.example.mynewsapp.ui.news

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.mynewsapp.R
import com.example.mynewsapp.data.model.Article
import com.example.mynewsapp.databinding.FragmentNewsBinding
import com.example.mynewsapp.ui.adapter.NewsAdapter
import com.example.mynewsapp.ui.adapter.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()
    private lateinit var searchView: SearchView

    private var _binding: FragmentNewsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding: FragmentNewsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        initRecyclerView()
        handleApiData()

    }

    private fun initRecyclerView() = binding.recyclerView.apply {
        setHasFixedSize(true)
        itemAnimator = null
        adapter = newsAdapter
    }


    private fun handleApiData() {
        viewModel.articles.observe(viewLifecycleOwner) {
            newsAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}