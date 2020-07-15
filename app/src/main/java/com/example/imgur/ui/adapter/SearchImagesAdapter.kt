package com.example.imgur.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imgur.R
import com.example.imgur.listeners.OnItemClickListener
import com.example.imgur.model.Image
import com.example.imgur.model.ImageData
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_search_items_layout.view.*
import java.lang.Exception


class SearchImagesAdapter(private val list: List<ImageData>,private val callback: OnItemClickListener<Image>) :
    RecyclerView.Adapter<SearchImagesAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_search_items_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.progress.visibility = View.VISIBLE
        if (list[position].images != null) {
            for (i in list[position].images?.indices!!) {
                Picasso.get()
                    .load(list[position].images?.get(i)?.link)
                    .fit()
                    .into(holder.itemView.imgSearchImage, object : Callback {
                        override fun onSuccess() {
                            if (holder.itemView.progress != null) {
                                holder.itemView.progress.visibility = View.GONE
                            }
                        }

                        override fun onError(e: Exception?) {

                        }

                    })

                holder.itemView.setOnClickListener{
                    list[position].images?.get(i)?.let { it1 -> callback.onItemClicked(it1) }
                }
            }
        }

    }
}