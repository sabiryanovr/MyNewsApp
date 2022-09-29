package com.example.mynewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mynewsapp.databinding.ActivityMainBinding
import com.example.mynewsapp.ui.BookmarksFragment
import com.example.mynewsapp.ui.BreakingNewsFragment
import com.example.mynewsapp.ui.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var breakingNewsFragment: BreakingNewsFragment
    private lateinit var bookmarksFragment: BookmarksFragment
    private lateinit var settingFragment: SettingFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(
            breakingNewsFragment,
            bookmarksFragment,
            settingFragment
        )

    private var selectedIndex = 0

    private val selectedFragment get() = fragments[selectedIndex]

    private fun selectFragment(selectedFragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (selectedFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit()

        title = when (selectedFragment) {
            is BreakingNewsFragment -> getString(R.string.title_breaking_news)
            is BookmarksFragment -> getString(R.string.title_bookmarks)
            is SettingFragment -> getString(R.string.title_setting)
            else -> ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            breakingNewsFragment = BreakingNewsFragment()
            bookmarksFragment = BookmarksFragment()
            settingFragment = SettingFragment()

            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, breakingNewsFragment, TAG_BREAKING_NEWS_FRAGMENT)
                .add(R.id.fragment_container, bookmarksFragment, TAG_BOOKMARKS_FRAGMENT)
                .add(R.id.fragment_container, settingFragment, TAG_SETTING_FRAGMENT)
                .commit()
        } else {
            breakingNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_BREAKING_NEWS_FRAGMENT) as BreakingNewsFragment
            bookmarksFragment =
                supportFragmentManager.findFragmentByTag(TAG_BOOKMARKS_FRAGMENT) as BookmarksFragment
            settingFragment =
                supportFragmentManager.findFragmentByTag(TAG_SETTING_FRAGMENT) as SettingFragment

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)
        }

        selectFragment(selectedFragment)

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_breaking -> breakingNewsFragment
                R.id.nav_bookmarks -> bookmarksFragment
                R.id.nav_setting -> settingFragment
                else -> throw IllegalArgumentException("Unexpected itemId")
            }

            if (selectedFragment === fragment) {
                if (fragment is OnBottomNavigationFragmentReselectedListener) {
                    fragment.onBottomNavigationFragmentReselected()
                }
            } else {
                selectFragment(fragment)
            }
            true
        }
    }

    interface OnBottomNavigationFragmentReselectedListener {
        fun onBottomNavigationFragmentReselected()
    }

    override fun onBackPressed() {
        if (selectedIndex != 0) {
            binding.bottomNav.selectedItemId = R.id.nav_breaking
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_INDEX, selectedIndex)
    }
}

private const val TAG_BREAKING_NEWS_FRAGMENT = "TAG_BREAKING_NEWS_FRAGMENT"
private const val TAG_BOOKMARKS_FRAGMENT = "TAG_BOOKMARKS_FRAGMENT"
private const val TAG_SETTING_FRAGMENT = "TAG_SETTING_FRAGMENT"
private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"
