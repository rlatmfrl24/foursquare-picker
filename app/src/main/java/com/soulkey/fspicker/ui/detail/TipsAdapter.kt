package com.soulkey.fspicker.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soulkey.fspicker.R
import com.soulkey.fspicker.model.Tip
import kotlinx.android.synthetic.main.item_tips.view.*

val TipDiff = object : DiffUtil.ItemCallback<Tip>(){
    override fun areItemsTheSame(oldItem: Tip, newItem: Tip): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Tip, newItem: Tip): Boolean {
        return oldItem == newItem
    }
}

class TipsAdapter : ListAdapter<Tip, TipsAdapter.TipViewHolder>(TipDiff){
    inner class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tip: Tip){
            itemView.tv_tips_user_name.text = tip.userName
            itemView.tv_tips_description.text = tip.description
            Glide.with(itemView).load(tip.iconLink)
                .circleCrop()
                .into(itemView.iv_tips_user_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        return TipViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tips, parent, false))
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}