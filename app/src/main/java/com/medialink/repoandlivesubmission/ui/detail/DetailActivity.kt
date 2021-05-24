package com.medialink.repoandlivesubmission.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.databinding.ActivityDetailBinding
import com.medialink.repoandlivesubmission.ui.detail.movie.MovieDetailFragment
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
                val movieDetailsFragment = MovieDetailFragment()
                supportFragmentManager.beginTransaction()
                    .add(R.id.movie_details_container, movieDetailsFragment)
                    .commit()
                Log.d("TAG", "onCreate: "+item.title)
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
