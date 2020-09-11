package com.amary.codexgamer.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.R
import com.amary.codexgamer.databinding.FragmentFavoBinding
import com.amary.codexgamer.utils.GamesConstant.adapterFavoriteGamesCallback
import com.ian.recyclerviewhelper.helper.setUpVerticalGridAdapter
import kotlinx.android.synthetic.main.item_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavoFragment : Fragment() {

    private val favoViewModel: FavoViewModel by viewModel()
    private lateinit var binding: FragmentFavoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favo, container, false)
        initFavoriteGames(binding)
        return binding.root
    }

    private fun initFavoriteGames(binding: FragmentFavoBinding) {
        binding.apply {
            ltLoadingFavorite.visibility = View.VISIBLE
            favoViewModel.getAllFavoriteGames()
                .observe(viewLifecycleOwner, { listFavorite ->
                    if (listFavorite.isNotEmpty()){
                        ltLoadingFavorite.visibility = View.GONE
                        rvFavorite.setUpVerticalGridAdapter(
                            listFavorite,
                            adapterFavoriteGamesCallback,
                            R.layout.item_list,
                            2,
                            {
                                val imageLoader = Coil.imageLoader(context)
                                val request = ImageRequest.Builder(context)
                                    .data(it.games.backgroundImage)
                                    .placeholder(R.drawable.img_placeholder)
                                    .target(iv_item_image)
                                    .build()
                                imageLoader.enqueue(request)
                                tv_item_title.text = it.games.name
                                tv_item_rating.text = it.games.rating.toString()
                            })
                    } else {
                        ltLoadingFavorite.visibility = View.GONE
                        viewErrorFavorite.visibility = View.VISIBLE
                    }

                })
        }
    }
}