package com.example.mynewsapp.ui

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.MainActivity
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentBookmarksBinding
import com.example.mynewsapp.databinding.FragmentSettingBinding
import com.example.mynewsapp.ui.adapter.NewsArticleListAdapter
import com.example.share.presentation.setting.SettingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment(R.layout.fragment_setting) {

    private val viewModel: SettingViewModel by viewModels()

    private var currentBinding: FragmentSettingBinding? = null
    private val binding get() = currentBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentBinding = FragmentSettingBinding.bind(view)

        binding.buttonBookmarks.setOnClickListener {
            try{
                viewModel.onDeleteAllBookmarks()
                showSnackbar(
                    getString(R.string.delete_bookmarks_news)
                )

            } catch (error: Throwable){
                showSnackbar("Error")
            }
        }
        binding.buttonBreakingNews.setOnClickListener {
            try{
                viewModel.onDeleteNonBookmarkedArticles()
                showSnackbar(
                    getString(R.string.delete_non_bookmarks_news)
                )

            } catch (error: Throwable){
                showSnackbar("Error")
            }
        }




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