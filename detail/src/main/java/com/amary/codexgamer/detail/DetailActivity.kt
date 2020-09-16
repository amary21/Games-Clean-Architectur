package com.amary.codexgamer.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.MainActivity.Companion.BUNDLE_KEY
import com.amary.codexgamer.detail.databinding.ActivityDetailBinding
import com.amary.codexgamer.domain.model.Games
import com.amary.codexgamer.utils.GamesConstant
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.ian.recyclerviewhelper.helper.setUpHorizontalListAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.item_screenshot.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(detailModule)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val games = intent.getParcelableExtra<Games>(BUNDLE_KEY)
        if (games != null) {
            initDetailGames(games, binding)
        }
    }

    @Suppress("DEPRECATION")
    @SuppressLint("SetJavaScriptEnabled")
    private fun initDetailGames(gamesData: Games, binding: ActivityDetailBinding) {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = gamesData.name
            rvScreenshot.setUpHorizontalListAdapter(
                gamesData.shortScreenshots,
                GamesConstant.adapterScreenShotCallback,
                R.layout.item_screenshot,
                {
                    val imageLoader = Coil.imageLoader(context)
                    val request = ImageRequest.Builder(context)
                        .data(it)
                        .placeholder(R.drawable.img_placeholder)
                        .target(img_screenshot)
                        .build()
                    imageLoader.enqueue(request)
                })

            detailGames = gamesData
            genreGames = gamesData.genres.joinToString(separator = ", ")
            platformGames = gamesData.platforms.joinToString(separator = ", ")
            storeGames = gamesData.stores.joinToString(separator = ", ")
            tvMinimumRequirement.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                    gamesData.minimumRequirement,
                    Html.FROM_HTML_MODE_COMPACT
                ) else Html.fromHtml(gamesData.minimumRequirement)
            tvRecomendedRequirement.text =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
                    gamesData.recommendedRequirement,
                    Html.FROM_HTML_MODE_COMPACT
                ) else Html.fromHtml(gamesData.recommendedRequirement)
            wbTrailer.setBackgroundColor(Color.BLACK)
            wbTrailer.settings.javaScriptEnabled = true
            wbTrailer.webChromeClient = WebChromeClient()
            wbTrailer.loadUrl(getString(R.string.youtube, gamesData.clip))

            detailViewModel.isFavorite(gamesData.id).observe(this@DetailActivity, {
                var isFavorite = it == 1
                setStatusFavorite(isFavorite, fabFavorite)
                fabFavorite.setOnClickListener {
                    if (isFavorite) {
                        detailViewModel.deleteFavorite(gamesData.id)
                    } else {
                        detailViewModel.insertFavorite(
                            com.amary.codexgamer.domain.model.Favorite(
                                gamesData.id
                            )
                        )
                    }

                    isFavorite = !isFavorite
                    setStatusFavorite(isFavorite, fab_favorite)
                }
            })
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean, fabFavorite: FloatingActionButton) {
        if (statusFavorite) {
            fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_menu_favorite
                )
            )
        } else {
            fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_menu_favorite_border
                )
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            onBackPressed()
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}