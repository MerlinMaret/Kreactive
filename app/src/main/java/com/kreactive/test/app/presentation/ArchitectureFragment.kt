package com.kreactive.test.app.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.kreactive.test.app.domain.Action
import com.kreactive.test.app.domain.Result

abstract class ArchitectureFragment<VS : ViewState, VM : BaseViewModel<out Action, VS, out Result>> : BaseFragment() {

    protected lateinit var viewModel: VM
        private set

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = initViewModel()
    }

    abstract fun initViewModel(): VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val observer = Observer<VS> { t -> render(t) }
        viewModel.viewState.observe(
            viewLifecycleOwner,
            observer
        )
        viewModel.viewState.value?.let { render(it) }
    }

    /**
     * Call this methode to update the view
     */
    abstract fun render(viewState: VS)

}
