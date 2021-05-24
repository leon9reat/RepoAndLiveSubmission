package com.medialink.repoandlivesubmission.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.medialink.repoandlivesubmission.R
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.databinding.FragmentBaseItemBinding
import com.medialink.repoandlivesubmission.utils.AppConfig

class BaseAdapter(private val mFragmentCallback: IBaseFragment) :
    RecyclerView.Adapter<BaseAdapter.MyViewHolder>() {

    private var mListData = arrayListOf<Detail>()

    fun setData(listData: List<Detail>?) {
        if (listData == null) return
        this.mListData.clear()
        this.mListData.addAll(listData)
    }

    inner class MyViewHolder(private val mBinding: FragmentBaseItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bind(detail: Detail) {
            val lblTanggal = when (detail.idType) {
                AppConfig.TYPE_MOVIE -> itemView.resources.getString(R.string.label_release)
                AppConfig.TYPE_TV_SHOW -> itemView.resources.getString(R.string.label_airing)
                else -> "Date"
            }

            with(mBinding) {
                tvTitleMovieList.text = detail.title
                tvDateMovie.text = lblTanggal + " " + detail.date.toString()
                tvVoteMovie.text = detail.voteAverage.toString()
                progressVoteMovie.progress = detail.voteAverage?.times(10)?.toInt() ?: 0
                tvOverviewMovie.text = detail.overview

                Glide.with(itemView.context)
                    .load(detail.posterPath)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgPosterMovieList)

                btnLike.setOnClickListener { mFragmentCallback.onFavoriteClick(detail) }
                btnShare.setOnClickListener { mFragmentCallback.onShareClick(detail) }
                itemView.setOnClickListener {
                    mFragmentCallback.onItemClick(detail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseAdapter.MyViewHolder {
        val binding = FragmentBaseItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseAdapter.MyViewHolder, position: Int) {
        val data = mListData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = mListData.size
}