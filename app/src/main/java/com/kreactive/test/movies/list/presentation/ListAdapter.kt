package com.kreactive.test.movies.list.presentation.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kreactive.test.R
import com.kreactive.test.movies.list.domain.ListAction
import com.kreactive.test.movies.list.model.MovieListItem
import com.kreactive.test.movies.list.presentation.ListViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(val viewModel: ListViewModel) :
    ListAdapter<MovieListItem?, RecyclerView.ViewHolder>(ListDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ViewType.ADD.ordinal) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_add_list, parent, false)
            return AddListViewHolder(view, viewModel)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            return ListViewHolder(view, viewModel)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ListViewHolder -> holder.bind(getItem(position))
            is AddListViewHolder -> holder.bind()
        }
    }

    override fun submitList(list: List<MovieListItem?>?) {
        val totalList: MutableList<MovieListItem?> = mutableListOf()
        list?.let { totalList.addAll(it) }
        if(list?.size ?: 0 > 0){
            totalList.add(null)
        }
        super.submitList(totalList)
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) != null) {
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

class ListViewHolder(view: View, val listViewModel: ListViewModel) : RecyclerView.ViewHolder(view) {

    fun bind(item: MovieListItem?) {
        itemView.item_tv_title.text = item?.title
        itemView.item_tv_year.text = item?.year
        Picasso.get().load(item?.posterUrl).into(itemView.item_iv_poster)
        itemView.setOnClickListener { listViewModel.action(ListAction.ClickItem(item?.id)) }
    }
}

class AddListViewHolder(view: View, val listViewModel: ListViewModel) :
    RecyclerView.ViewHolder(view) {

    fun bind() {
        itemView.setOnClickListener { listViewModel.addOnList() }
    }
}

class ListDiffUtils : DiffUtil.ItemCallback<MovieListItem?>() {
    override fun areItemsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieListItem, newItem: MovieListItem): Boolean {
        oldItem.equals(newItem)
        return true
    }

}
