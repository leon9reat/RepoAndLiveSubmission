package com.medialink.repoandlivesubmission.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.repository.IRepository
import com.medialink.repoandlivesubmission.data.source.remote.repository.MovieRepository
import com.medialink.repoandlivesubmission.data.source.remote.repository.TvShowRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.RetrofitClient
import com.medialink.repoandlivesubmission.databinding.FragmentBaseBinding
import com.medialink.repoandlivesubmission.ui.detail.DetailActivity
import com.medialink.repoandlivesubmission.utils.AppConfig
import com.medialink.repoandlivesubmission.utils.Status
import com.medialink.repoandlivesubmission.viewmodel.Factory


class BaseFragment : Fragment(), IBaseFragment {

    companion object {
        const val PARAMETER: String = "PARAMETER"

        @JvmStatic
        fun newInstance(idJenis: Int) =
            BaseFragment().apply {
                arguments = Bundle().apply {
                    putInt(PARAMETER, idJenis)
                }
            }
    }

    private lateinit var binding: FragmentBaseBinding
    private lateinit var mViewModel: FragmentViewModel
    private var mIdJenis: Int = 0

    val mAdapter = BaseAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mIdJenis = it.getInt(PARAMETER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            setupViewModel()
            setupUI()
            setupObserver()
        }
    }

    private fun setupViewModel() {
        val apiService = RetrofitClient.getApiService()

        when (mIdJenis) {
            AppConfig.TYPE_MOVIE -> {
                val movieRepo = MovieRepository(apiService)
                val factory = Factory.getFactory(movieRepo)
                mViewModel = ViewModelProvider(this, factory).get(FragmentViewModel::class.java)
            }
            AppConfig.TYPE_TV_SHOW -> {
                val tvRepo = TvShowRepository(apiService)
                val factory = Factory.getFactory(tvRepo)
                mViewModel = ViewModelProvider(this, factory).get(FragmentViewModel::class.java)
            }
        }
    }

    private fun setupObserver() {
        mViewModel.getList().observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { listData ->
                        if (listData.size > 0) {
                            binding.moviesRv.visibility = View.VISIBLE
                            binding.layoutEmpty.root.visibility = View.GONE
                            binding.layoutError.root.visibility = View.GONE
                            renderList(listData)
                        } else {
                            binding.moviesRv.visibility = View.GONE
                            binding.layoutEmpty.root.visibility = View.VISIBLE
                            binding.layoutError.root.visibility = View.GONE
                        }
                    }

                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.moviesRv.visibility = View.GONE
                    binding.layoutEmpty.root.visibility = View.GONE
                    binding.layoutError.root.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    binding.moviesRv.visibility = View.GONE
                    binding.layoutEmpty.root.visibility = View.GONE
                    binding.layoutError.root.visibility = View.VISIBLE
                    binding.layoutError.tvError.text = it.message
                    //Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(listData: List<Detail>) {
        mAdapter.setData(listData)
        mAdapter.notifyDataSetChanged()
    }

    private fun setupUI() {

        with(binding.moviesRv) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    override fun onItemClick(detail: Detail) {
        val i = Intent(activity, DetailActivity::class.java)
        i.putExtra(DetailActivity.PARCEL_DETAIL, detail)
        startActivity(i)
    }

    override fun onShareClick(detail: Detail) {
        Snackbar.make(
            binding.myCoordinatorLayout,
            "Share",
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onFavoriteClick(detail: Detail) {
        Snackbar.make(
            binding.myCoordinatorLayout,
            "Favorite",
            Snackbar.LENGTH_SHORT
        ).show()
    }
}
