package com.amary.codexgamer.ui.detail

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
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
            genreGames = args.gamesData.genres.joinToString(separator = ", ")
            platformGames = args.gamesData.platforms.joinToString(separator = ", ")
            storeGames = args.gamesData.stores.joinToString(separator = ", ")
            tvMinimumRequirement.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                    args.gamesData.minimumRequirement,
                    Html.FROM_HTML_MODE_COMPACT
                ) else Html.fromHtml(args.gamesData.minimumRequirement)
            tvRecomendedRequirement.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                    args.gamesData.recommendedRequirement,
                    Html.FROM_HTML_MODE_COMPACT
                ) else Html.fromHtml(args.gamesData.recommendedRequirement)
            wbTrailer.settings.javaScriptEnabled = true
            wbTrailer.webChromeClient = WebChromeClient()
            wbTrailer.loadUrl(getString(R.string.youtube) + args.gamesData.clip)

            detailViewModel.isFavorite(args.gamesData.id).observe(viewLifecycleOwner, {
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