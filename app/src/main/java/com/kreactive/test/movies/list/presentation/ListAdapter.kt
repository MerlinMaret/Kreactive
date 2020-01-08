package com.kreactive.test.movies.list.presentation.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kreactive.test.R
import com.kreactive.test.movies.list.model.Movie
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter : ListAdapter<Movie, ListViewHolder>(ListDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: Movie) {
        itemView.item_title.text = item.title
    }
}

class ListDiffUtils : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        oldItem.equals(newItem)
        return true
    }

}
