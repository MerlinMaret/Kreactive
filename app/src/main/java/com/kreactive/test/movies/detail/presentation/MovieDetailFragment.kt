package com.kreactive.test.movies.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kreactive.test.app.presentation.ArchitectureFragment
import com.kreactive.test.R
import com.kreactive.test.movies.detail.domain.MovieDetailAction
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_moviedetail.*

class MovieDetailFragment : ArchitectureFragment<MovieDetailViewState, MovieDetailViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moviedetail, container, false)
    }

    override fun initViewModel(): MovieDetailViewModel {
        return MovieDetailViewModel.getFragmentInstance(this)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            val bundle = MovieDetailFragmentArgs.fromBundle(it)
            val movieId = bundle.movieId
            viewModel.action(MovieDetailAction.InitView(movieId))
        }
    }

    //region render

    override fun render(viewState: MovieDetailViewState) {
        tv_title.text = viewState.title
        tv_actors.text = viewState.actors
        Picasso.get().load(viewState.posterUrl).into(iv_poster)
    }

    //endregion
}
