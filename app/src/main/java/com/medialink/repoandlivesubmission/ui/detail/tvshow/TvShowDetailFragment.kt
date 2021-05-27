package com.medialink.repoandlivesubmission.ui.detail.tvshow

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.entity.Review
import com.medialink.repoandlivesubmission.data.source.remote.entity.Video
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.Movie
import com.medialink.repoandlivesubmission.data.source.remote.entity.tvshow.TvShow
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.RetrofitClient
import com.medialink.repoandlivesubmission.databinding.FragmentTvShowDetailBinding
import com.medialink.repoandlivesubmission.ui.detail.movie.MovieDetailViewModel
import com.medialink.repoandlivesubmission.ui.detail.movie.MovieReviewAdapter
import com.medialink.repoandlivesubmission.ui.fragment.BaseFragment
import com.medialink.repoandlivesubmission.utils.Status
import com.medialink.repoandlivesubmission.viewmodel.ViewModelFactory


class TvShowDetailFragment : Fragment() {
    private lateinit var binding: FragmentTvShowDetailBinding
    private lateinit var viewModel: TvShowDetailViewModel
    private var mTvShow: Detail? = null

    companion object {
        const val PARAMETER: String = "PARAMETER"

        @JvmStatic
        fun newInstance(tvShow: Detail) =
            TvShowDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PARAMETER, tvShow)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            mTvShow = bundle.getParcelable(BaseFragment.PARAMETER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowDetailBinding.inflate(layoutInflater, container, false)

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupViewModel(RetrofitClient.getApiService())
            setupUi()
            setToolbar()
            setupObserver()

            mTvShow?.id?.let {
                viewModel.fetchTvShow(it)
                viewModel.fetchTvReview(it)
                viewModel.fetchVideo(it)
            }
        }

    }

    private fun setupUi() {
        binding.favorite.setOnClickListener {
            Snackbar.make(
                binding.root,
                "Favorite",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setupObserver() {
        viewModel.getTvShow().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { tvShow -> renderData(tvShow) }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.getReview().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { review -> showReviews(review) }
                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

        viewModel.getVideo().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { video -> showTrailers(video) }
                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderData(tvshow: TvShow) {
        Glide.with(requireActivity())
            .load(String.format(ApiConfig.BASR_BACKDROP_PATH, tvshow.backdropPath))
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding.ivBackdrop)

        with(binding) {
            tvName.text = tvshow.name
            tvFirstAirDate.text = getString(R.string.label_airing) + " " + tvshow.firstAirDate
            tvRating.text = String.format(getString(R.string.rating), tvshow.voteAverage)
            tvOverview.text = tvshow.overview
        }
    }

    private fun showReviews(reviews: List<Review>) {
        if (reviews.isEmpty()) {
            binding.trailerAndReviews.reviews.visibility = View.GONE
            binding.trailerAndReviews.reviewsLabel.visibility = View.GONE
        } else {
            binding.trailerAndReviews.reviews.visibility = View.VISIBLE
            binding.trailerAndReviews.reviewsLabel.visibility = View.VISIBLE

            val adapter = MovieReviewAdapter(requireContext(), reviews as ArrayList<Review>)
            binding.trailerAndReviews.reviews.adapter = adapter
        }
    }

    private fun showTrailers(video: List<Video>) {
        if (video.isEmpty()) {
            binding.trailerAndReviews.trailersLabel.visibility = View.GONE
            binding.trailerAndReviews.trailers.visibility = View.GONE
            binding.trailerAndReviews.trailersContainer.visibility = View.GONE
        } else {
            binding.trailerAndReviews.trailersLabel.visibility = View.VISIBLE
            binding.trailerAndReviews.trailers.visibility = View.VISIBLE
            binding.trailerAndReviews.trailersContainer.visibility = View.VISIBLE

            binding.trailerAndReviews.trailers.removeAllViews()

            val inflater = requireActivity().layoutInflater
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .override(150, 150)
            for (trailer in video) {
                val thumbContainer: View = inflater.inflate(
                    R.layout.video,
                    binding.trailerAndReviews.trailers, false
                )
                val thumbView: ImageView = thumbContainer.findViewById(R.id.video_thumb)
                thumbView.requestLayout()
                thumbView.tag = String.format(ApiConfig.YOUTUBE_VIDEO_URL, trailer.key.toString())
                thumbView.setOnClickListener { v ->
                    val videoUrl = v?.tag.toString()
                    val playVideoIntent = Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl))
                    startActivity(playVideoIntent)
                }
                Glide.with(requireContext())
                    .load(String.format(ApiConfig.YOUTUBE_THUMBNAIL_URL, trailer.key))
                    .apply(options)
                    .into(thumbView)

                binding.trailerAndReviews.trailers.addView(thumbContainer)
            }
        }
    }

    private fun setupViewModel(apiService: ApiService) {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(apiService)
        ).get(TvShowDetailViewModel::class.java)
    }

    private fun setToolbar() {
        with(binding) {
            collapsingToolbar.title = getString(R.string.movie_details)
            collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedToolbar)
            collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedToolbar)
            collapsingToolbar.isTitleEnabled = true
        }
    }
}