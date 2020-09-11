package com.amary.codexgamer.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.favorite.databinding.FavoriteFragmentBinding
import com.amary.codexgamer.utils.GamesConstant
import com.ian.recyclerviewhelper.helper.setUpVerticalGridAdapter
import kotlinx.android.synthetic.main.item_list.view.*
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by inject()
    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(favoriteModule)
        binding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)
        initFavoriteGames(binding)
        return binding.root
    }

    private fun initFavoriteGames(binding: FavoriteFragmentBinding) {
        binding.apply {
            ltLoadingFavorite.visibility = View.VISIBLE
            favoriteViewModel.getAllFavoriteGames()
                .observe(viewLifecycleOwner, { listFavorite ->
                    if (listFavorite.isNotEmpty()){
                        ltLoadingFavorite.visibility = View.GONE
                        rvFavorite.setUpVerticalGridAdapter(
                            listFavorite,
                            GamesConstant.adapterFavoriteGamesCallback,
                            R.layout.item_list,
                            2,
                            {
                                val imageLoader = Coil.imageLoader(context)
                                val request = ImageRequest.Builder(context)
                                    .data(it.games.backgroundImage)
                                    .placeholder(R.drawable.img_placeholder)
                                    .target(iv_item_image_favorite)
                                    .build()
                                imageLoader.enqueue(request)
                                tv_item_title_favorite.text = it.games.name
                                tv_item_rating_favorite.text = it.games.rating.toString()
                            },{
                                val uri = Uri.parse("codexgamer://detail?gamesId=${this.games.id}")
                                startActivity(Intent(Intent.ACTION_VIEW, uri))
                            })
                    } else {
                        ltLoadingFavorite.visibility = View.GONE
                        viewErrorFavorite.visibility = View.VISIBLE
                    }

                })
        }
    }

}