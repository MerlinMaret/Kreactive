package com.kreactive.test.movies.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kreactive.test.app.presentation.ArchitectureFragment
import com.kreactive.test.R
import com.kreactive.test.movies.list.domain.ListAction
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : ArchitectureFragment<ListViewState, ListViewModel>() {

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

    override fun initView(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val bundle = ListFragmentArgs.fromBundle(it)
            val search = bundle.search
            viewModel.action(ListAction.ViewInit(search))
        }
    }

    //region render

    override fun render(viewState: ListViewState) {
        renderSearch(viewState)
    }

    private fun renderSearch(viewState: ListViewState){
        if(viewState.search != et_search.text.toString()){
            et_search.setText(viewState.search)
        }
    }

    //endregion
}
