package com.medialink.repoandlivesubmission.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.databinding.ActivityDetailBinding
import com.medialink.repoandlivesubmission.ui.detail.movie.MovieDetailFragment
import com.medialink.repoandlivesubmission.ui.detail.tvshow.TvShowDetailFragment
import com.medialink.repoandlivesubmission.utils.AppConfig


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val PARCEL_DETAIL = "parcel_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val item: Detail? = intent.getParcelableExtra(PARCEL_DETAIL)

        if (item != null) {
            if (item.idType == AppConfig.TYPE_MOVIE) {
                val movieDetailsFragment = MovieDetailFragment.newInstance(item)
                supportFragmentManager.beginTransaction()
                    .replace(R.id.details_container, movieDetailsFragment)
                    .commit()
            } else if (item.idType == AppConfig.TYPE_TV_SHOW) {
                val tvDetailFragment = TvShowDetailFragment.newInstance(item)
                Log.d("TAG", "onCreate: ${item.id}")
                supportFragmentManager.beginTransaction()
                    .replace(R.id.details_container, tvDetailFragment)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
