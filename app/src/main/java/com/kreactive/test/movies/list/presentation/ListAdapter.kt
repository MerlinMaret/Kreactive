package com.kreactive.test.movies.list.presentation.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kreactive.test.R
import com.kreactive.test.movies.list.model.MovieListItem
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter : ListAdapter<MovieListItem, ListViewHolder>(ListDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun getItemViewType(position: Int): Int {
        if (position < itemCount) {
            return ViewType.MOVIE.ordinal
        } else {
            return ViewType.ADD.ordinal
        }
    }

    enum class ViewType {
        MOVIE,
        ADD
    }
}

class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: MovieListItem) {
        itemView.item_title.text = item.title
    }
}

class ListDiffUtils : DiffUtil.ItemCallback<MovieListItem>() {
    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        oldItem.equals(newItem)
        return true
    }

}
