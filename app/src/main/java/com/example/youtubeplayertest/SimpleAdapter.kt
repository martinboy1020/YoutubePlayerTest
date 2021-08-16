package com.example.youtubeplayertest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter(
    private val callback: ((String) -> Unit)? = null,
    private val layoutResId: Int
) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {

    private val items = DataSource.items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_item_main, parent, false)
        )


    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position].also { item ->
//            holder.bind(item)
            holder.textTitle.text = items[position].name
            holder.textSubtitle.text = items[position].subname
            holder.itemView.setOnClickListener {
                callback?.invoke(item.previewUrl)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle = view.findViewById<TextView>(R.id.textTitle)
        val textSubtitle = view.findViewById<TextView>(R.id.textSubtitle)
        val imageLogo = view.findViewById<ImageView>(R.id.imageLogo)

//        fun bind(item: Item) {
//            Picasso.get()
//                .load(item.previewUrl)
//                .into(itemView.imagePreview)
//
//            itemView.textTitle.text = item.name
//            itemView.textSubtitle.text = item.subname
//            itemView.imageLogo?.setImageResource(item.thumbID)
//        }
    }
}