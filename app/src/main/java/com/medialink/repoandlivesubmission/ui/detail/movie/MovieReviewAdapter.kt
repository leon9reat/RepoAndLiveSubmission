package com.medialink.repoandlivesubmission.ui.detail.movie

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.medialink.repoandlivesubmission.data.source.remote.entity.Review
import com.medialink.repoandlivesubmission.databinding.ReviewBinding

//Class MyAdapter
class MovieReviewAdapter(private val context: Context,
                         private val listReview: ArrayList<Review>) : BaseAdapter() {

    override fun getCount(): Int {
        return listReview.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ReviewBinding.inflate(LayoutInflater.from(context), parent, false)
        binding.reviewAuthor.text = listReview[position].author
        binding.reviewContent.text = listReview[position].content
        return binding.root
    }

}
//Class MyData class MyData(var num: Int, var name: String, var mobileNumber: String)