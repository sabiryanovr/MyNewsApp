package com.example.mynewsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.MainActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentBreakingNewsBinding
import com.example.mynewsapp.ui.adapter.NewsArticleListAdapter
import com.example.share.presentation.breakingnews.BreakingViewModel
import com.example.share.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news),
    MainActivity.OnBottomNavigationFragmentReselectedListener{
    private var currentBinding: FragmentBreakingNewsBinding? = null
    private val binding get() = currentBinding!!
    private val viewModel: BreakingViewModel by viewModels()
    private val newsArticleAdapter = NewsArticleListAdapter(
        onItemClick = { article ->
            val uri = Uri.parse(article.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireActivity().startActivity(intent)
        },
        onBookmarkClick = { article ->
            viewModel.onBookmarkClick(article)
        }
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentBreakingNewsBinding.bind(view)
        newsArticleAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.apply {
            recyclerView.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
                itemAnimator?.changeDuration = 0
            }
            viewModel.uiStateLiveData
                .distinctUntilChanged()
                .observe(viewLifecycleOwner) { render(it) }

            }

        binding.swipeRefreshLayout.setOnRefreshListener {
                viewModel.onManualRefresh()
            }

        binding.buttonRetry.setOnClickListener {
                viewModel.onManualRefresh()
            }

         }


private fun render(uiStateView: BreakingViewModel.UiStateView) {
    binding.swipeRefreshLayout.isRefreshing = uiStateView is BreakingViewModel.UiStateView.Loading
    binding.recyclerView.isVisible = uiStateView is BreakingViewModel.UiStateView.Data
    binding.textViewError.isVisible = uiStateView is BreakingViewModel.UiStateView.Error
    binding.buttonRetry.isVisible = uiStateView is BreakingViewModel.UiStateView.Error

    when (uiStateView) {
        is BreakingViewModel.UiStateView.Data -> {
            viewModel.updateBreakingNews()
            newsArticleAdapter.submitList(uiStateView.news)
        }
        is BreakingViewModel.UiStateView.Error -> {
            showSnackbar(
                getString(
                    R.string.could_not_refresh
                )
            )

        }
        BreakingViewModel.UiStateView.Loading -> Unit
    }
}

    override fun onBottomNavigationFragmentReselected() {
        binding.recyclerView.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentBinding = null
    }

    private fun Fragment.showSnackbar(
        message: String,
        duration: Int = Snackbar.LENGTH_LONG,
        view: View = requireView()
    ) {
        Snackbar.make(view, message, duration).show()
    }
}