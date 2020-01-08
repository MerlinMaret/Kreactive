package com.kreactive.test.movies.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.kreactive.test.app.presentation.ArchitectureFragment
import com.kreactive.test.R
import com.kreactive.test.movies.list.domain.ListAction
import com.kreactive.test.movies.list.presentation.presentation.ListAdapter
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : ArchitectureFragment<ListViewState, ListViewModel>() {

    private lateinit var adapter : ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun initViewModel(): ListViewModel {
        return ListViewModel.getFragmentInstance(this)
    }

    override fun initView(view: View, savedInstanceState: Bundle?)
    {
        adapter = ListAdapter(viewModel)
        rv_movies.adapter = adapter
        et_search.addTextChangedListener {
            val action = ListAction.SearchChange(it.toString())
            viewModel.action(action)
        }

        b_search.setOnClickListener {
            viewModel.action(ListAction.ClickSearch(et_search.text.toString()))
        }
    }

    //region render

    override fun render(viewState: ListViewState) {
        renderFirstLoad(viewState)
        renderSearch(viewState)
        renderList(viewState)
    }

    private fun renderFirstLoad(viewState: ListViewState) {
        if (viewState.firstLoad.get()){
            arguments?.let {
                val bundle = ListFragmentArgs.fromBundle(it)
                val search = bundle.search
                viewModel.action(ListAction.FirstInit(search))
            }
        }
    }

    private fun renderSearch(viewState: ListViewState){
        if(viewState.search != et_search.text.toString()){
            et_search.setText(viewState.search)
        }
    }

    private fun renderList(viewState: ListViewState){
        adapter.submitList(viewState.movies)
    }

    //endregion
}
