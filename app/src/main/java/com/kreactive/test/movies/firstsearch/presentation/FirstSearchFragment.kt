package com.kreactive.test.movies.firstsearch.presentation

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.kreactive.test.app.presentation.ArchitectureFragment
import com.kreactive.test.R
import com.kreactive.test.app.domain.BooleanOneTimeEvent
import com.kreactive.test.movies.firstsearch.domain.FirstSearchAction
import com.kreactive.test.movies.list.presentation.ListFragmentArgs
import kotlinx.android.synthetic.main.fragment_firstsearch.*

class FirstSearchFragment : ArchitectureFragment<FirstSearchViewState, FirstSearchViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_firstsearch, container, false)
    }

    override fun initViewModel(): FirstSearchViewModel {
        return FirstSearchViewModel.getFragmentInstance(this)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        et_search.addTextChangedListener {
            val action = FirstSearchAction.SearchChange(it.toString())
            viewModel.action(action)
        }

        b_search.setOnClickListener {
            viewModel.action(FirstSearchAction.ClickSearch(et_search.text.toString()))
        }
    }

    //region render

    override fun render(viewState: FirstSearchViewState) {
        renderSearchButton(viewState.search)
        renderEmptySearch(viewState.emptySearchError)
        renderGoToList(viewState)
    }

    private fun renderSearchButton(search: String) {
        b_search.visibility = if(TextUtils.isEmpty(search)) GONE else VISIBLE
    }

    private fun renderEmptySearch(emptySearchError: BooleanOneTimeEvent) {
        if(emptySearchError.get()){
            this.view?.let {view -> Snackbar.make(view,getString(R.string.empty_search_error), Snackbar.LENGTH_SHORT).show() }
        }
    }

    private fun renderGoToList(viewState: FirstSearchViewState) {
        if(viewState.goToList.get()){
            val action = FirstSearchFragmentDirections.goToListFragment()
            action.search = viewState.search
            findNavController().navigate(action)
        }
    }

    //endregion
}
