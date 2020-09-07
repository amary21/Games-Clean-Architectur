package com.amary.codexgamer.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.R
import com.amary.codexgamer.core.data.Resource
import com.amary.codexgamer.databinding.FragmentFavoriteBinding
import com.amary.codexgamer.utils.GamesConstant.adapterFavoriteGamesCallback
import com.ian.recyclerviewhelper.helper.setUpPagingWithGrid
import kotlinx.android.synthetic.main.item_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        initFavoriteGames(binding)
        return binding.root
    }

    private fun initFavoriteGames(binding: FragmentFavoriteBinding) {
        binding.apply {
            favoriteViewModel.getAllFavoriteGames()
                .observe(viewLifecycleOwner, Observer { pagedListFavorite ->
                    when (pagedListFavorite) {
                        is Resource.Loading -> {
                        }
                        is Resource.Success -> {
                            rvFavorite.setUpPagingWithGrid(
                                pagedListFavorite.data,
                                R.layout.item_list,
                                2,
                                {
                                    val imageLoader = Coil.imageLoader(context)
                                    val request = ImageRequest.Builder(context)
                                        .data(it.games.backgroundImage)
                                        .placeholder(com.amary.codexgamer.core.R.drawable.img_placeholder)
                                        .target(iv_item_image)
                                        .build()
                                    imageLoader.enqueue(request)
                                    tv_item_title.text = it.games.name
                                    tv_item_rating.text = it.games.rating.toString()
                                },
                                adapterFavoriteGamesCallback,
                                {

                                })
                        }
                        is Resource.Error -> {
                        }
                    }
                })
        }
    }
}