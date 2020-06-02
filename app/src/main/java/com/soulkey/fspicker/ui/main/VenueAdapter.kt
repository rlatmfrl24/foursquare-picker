package com.soulkey.fspicker.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soulkey.fspicker.R
import com.soulkey.fspicker.model.RecommendedVenue
import com.soulkey.fspicker.ui.detail.VenueDetailActivity
import kotlinx.android.synthetic.main.item_venue.view.*
import timber.log.Timber

val VenueDiff = object: DiffUtil.ItemCallback<RecommendedVenue>(){
    override fun areItemsTheSame(oldItem: RecommendedVenue, newItem: RecommendedVenue): Boolean {
        return oldItem.fsId == newItem.fsId
    }

    override fun areContentsTheSame(oldItem: RecommendedVenue, newItem: RecommendedVenue): Boolean {
        return oldItem == newItem
    }
}

class VenueAdapter :
    ListAdapter<RecommendedVenue, VenueAdapter.VenueViewHolder>(VenueDiff) {
    inner class VenueViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(venue: RecommendedVenue){
            itemView.tv_venue_name.text = venue.name
            itemView.tv_venue_address.text = venue.address
            Glide.with(itemView).load(venue.iconLink).centerCrop().into(itemView.iv_venue_photo)
            itemView.setOnClickListener {
                Intent(itemView.context, VenueDetailActivity::class.java).also { intent ->
                    intent.putExtra("fsId", venue.fsId)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        return VenueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_venue, parent, false))
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}