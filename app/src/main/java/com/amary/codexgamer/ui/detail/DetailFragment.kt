package com.amary.codexgamer.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.R
import com.amary.codexgamer.core.domain.model.Favorite
import com.amary.codexgamer.databinding.FragmentDetailBinding
import com.amary.codexgamer.ui.MainActivity
import com.amary.codexgamer.utils.GamesConstant.adapterScreenShotCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ian.recyclerviewhelper.helper.setUpHorizontalListAdapter
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_screenshot.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        val args = arguments?.let { DetailFragmentArgs.fromBundle(it) }
        args?.let { initDetailGames(it, binding) }
        return binding.root
    }

    private fun initDetailGames(args: DetailFragmentArgs, binding: FragmentDetailBinding) {
        binding.apply {
            (activity as MainActivity).supportActionBar?.title = args.gamesData.name
            rvScreenshot.setUpHorizontalListAdapter(
                args.gamesData.shortScreenshots,
                adapterScreenShotCallback,
                R.layout.item_screenshot,
                {
                    val imageLoader = Coil.imageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(it)
                        .placeholder(com.amary.codexgamer.core.R.drawable.img_placeholder)
                        .target(img_screenshot)
                        .build()
                    imageLoader.enqueue(request)
                })
            detailGames = args.gamesData

            detailViewModel.isFavorite(args.gamesData.id).observe(viewLifecycleOwner, Observer {
                var isFavorite = it == 1
                setStatusFavorite(isFavorite, fabFavorite)
                fabFavorite.setOnClickListener {
                    if (isFavorite) {
                        detailViewModel.deleteFavorite(args.gamesData.id)
                    } else {
                        detailViewModel.insertFavorite(Favorite(args.gamesData.id))
                    }

                    isFavorite = !isFavorite
                    setStatusFavorite(isFavorite, fab_favorite)
                }
            })
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean, fabFavorite: FloatingActionButton) {
        if (statusFavorite) {
            fabFavorite.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_menu_favorite
                )
            })
        } else {
            fabFavorite.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_menu_favorite_border
                )
            })
        }

    }
}