package com.medialink.repoandlivesubmission.ui.main

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.remote.repository.MovieRepository
import com.medialink.repoandlivesubmission.data.source.remote.repository.TvShowRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.RetrofitClient
import com.medialink.repoandlivesubmission.databinding.ActivityMainBinding
import com.medialink.repoandlivesubmission.ui.fragment.BaseFragment
import com.medialink.repoandlivesubmission.utils.AppConfig

class MainActivity : AppCompatActivity(), IMainActivity {

    companion object {
        @StringRes
        private val TAB_TITLE = intArrayOf(R.string.movie, R.string.tv_show)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        with(binding) {
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(binding.viewPager)
        }
        supportActionBar?.elevation = 0f
    }

    override fun getCount(): Int = TAB_TITLE.size

    override fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> BaseFragment.newInstance(AppConfig.TYPE_MOVIE)
            1 -> BaseFragment.newInstance(AppConfig.TYPE_TV_SHOW)
            else -> Fragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence =
        resources.getString(TAB_TITLE[position])
}