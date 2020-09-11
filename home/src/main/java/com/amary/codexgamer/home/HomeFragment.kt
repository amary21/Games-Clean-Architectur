package com.amary.codexgamer.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.domain.model.ResourceState
import com.amary.codexgamer.home.databinding.FragmentHomeBinding
import com.amary.codexgamer.MainActivity
import com.amary.codexgamer.utils.GamesConstant.adapterGamesCallback
import com.ian.recyclerviewhelper.helper.setUpPagingWithGrid
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(homeModule)
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initListGames(binding)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.main, menu)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val item = menu.findItem(R.id.action_search)
        val searchView = SearchView((context as MainActivity).supportActionBar?.themedContext)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = getString(R.string.action_search)
        searchView.setOnQueryTextListener(this)
        item.actionView = searchView
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        if (query.isNotEmpty()) {
            initListGames(binding, query)
        }

        return true
    }

    override fun onQueryTextChange(query: String?) = false


    private fun initListGames(binding: FragmentHomeBinding, searchKey: String = "") {
        binding.apply {
            ltLoading.visibility = View.VISIBLE
            homeViewModel.getGames(searchKey)
                .observe(viewLifecycleOwner, { pagedListGames ->
                    if (pagedListGames != null){
                        rvHome.setUpPagingWithGrid(
                            pagedListGames,
                            R.layout.item_list,
                            2,
                            {
                                val imageLoader = Coil.imageLoader(context)
                                val request = ImageRequest.Builder(context)
                                    .data(it.backgroundImage)
                                    .placeholder(R.drawable.img_placeholder)
                                    .target(iv_item_image)
                                    .build()
                                imageLoader.enqueue(request)
                                tv_item_title.text = it.name
                                tv_item_rating.text = it.rating.toString()
                            },
                            adapterGamesCallback,
                            {
                                val uri = Uri.parse("codexgamer://detail?gamesId=${this.id}")
                                startActivity(Intent(Intent.ACTION_VIEW, uri))
                            })
                    }
                })

            homeViewModel.getResourceState().observe(viewLifecycleOwner, {
                when(it){
                    ResourceState.LOADING -> {
                        ltLoading.visibility = View.VISIBLE
                        rvHome.visibility = View.VISIBLE
                        viewError.visibility = View.GONE
                    }
                    ResourceState.LOADED -> {
                        ltLoading.visibility = View.GONE
                        rvHome.visibility = View.VISIBLE
                        viewError.visibility = View.GONE
                    }
                    ResourceState.ERROR -> {
                        ltLoading.visibility = View.GONE
                        rvHome.visibility = View.GONE
                        viewError.visibility = View.VISIBLE
                        tv_no_data.text = it.message
                    }
                }
            })
        }
    }
}