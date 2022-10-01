package com.example.myapplication.navigation

import com.example.myapplication.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Breaking : NavigationItem("breaking", R.drawable.ic_breaking, "Breaking")
    object Bookmarks : NavigationItem("bookmarks", R.drawable.ic_bookmarks, "Bookmarks")
    object Setting : NavigationItem("setting", R.drawable.ic_baseline_settings_24, "Setting")
}
