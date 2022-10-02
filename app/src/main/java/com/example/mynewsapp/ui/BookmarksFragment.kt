package com.example.mynewsapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.MainActivity
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentBookmarksBinding
import com.example.mynewsapp.ui.adapter.NewsArticleListAdapter
import com.example.share.presentation.bookmarks.BookmarksViewModel
import com.example.share.presentation.breakingnews.BreakingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks),
    MainActivity.OnBottomNavigationFragmentReselectedListener {

    private val viewModel: BookmarksViewModel by viewModels()
    private val bookmarksAdapter = NewsArticleListAdapter(
        onItemClick = { article ->
            val uri = Uri.parse(article.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            requireActivity().startActivity(intent)
        },
        onBookmarkClick = { article ->
            viewModel.onBookmarkClick(article)
        }
    )
    private var currentBinding: FragmentBookmarksBinding? = null
    private val binding get() = currentBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentBinding = FragmentBookmarksBinding.bind(view)


        binding.apply {
            recyclerView.apply {
                adapter = bookmarksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.uiStateLiveData
                .distinctUntilChanged()
                .observe(viewLifecycleOwner) { render(it) }
        }

        setupMenu()
//        setHasOptionsMenu(true)
    }
private fun render(uiStateView: BookmarksViewModel.UiStateView) {

    when (uiStateView) {
        is BookmarksViewModel.UiStateView.Data -> {
            viewModel.updateBookmarksNews()
            bookmarksAdapter.submitList(uiStateView.news)
        }
        is BookmarksViewModel.UiStateView.Error -> {
            showSnackbar(
                getString(
                    R.string.could_not_refresh
                )
            )

        }
        BookmarksViewModel.UiStateView.Loading -> Unit
    }
}
    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_bookmarks, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem) =
                when (menuItem.itemId) {
                    R.id.action_delete_all_bookmarks -> {
                        viewModel.onDeleteAllBookmarks()
                        true
                    }
                    else -> false
                }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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