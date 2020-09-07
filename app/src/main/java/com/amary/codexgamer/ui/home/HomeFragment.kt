package com.amary.codexgamer.ui.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import coil.Coil
import coil.request.ImageRequest
import com.amary.codexgamer.R
import com.amary.codexgamer.core.data.Resource
import com.amary.codexgamer.databinding.FragmentHomeBinding
import com.amary.codexgamer.ui.MainActivity
import com.amary.codexgamer.utils.GamesConstant.adapterGamesCallback
import com.ian.recyclerviewhelper.helper.setUpPagingWithGrid
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_list.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
                .observe(viewLifecycleOwner, Observer { pagegListGames ->
                    if (pagegListGames != null) {
                        when (pagegListGames) {
                            is Resource.Loading -> {
                                lt_loading.visibility = View.VISIBLE
                            }
                            is Resource.Success -> {
                                rvHome.setUpPagingWithGrid(
                                    pagegListGames.data,
                                    R.layout.item_list,
                                    2,
                                    {
                                        val imageLoader = Coil.imageLoader(context)
                                        val request = ImageRequest.Builder(context)
                                            .data(it.backgroundImage)
                                            .placeholder(com.amary.codexgamer.core.R.drawable.img_placeholder)
                                            .target(iv_item_image)
                                            .build()
                                        imageLoader.enqueue(request)
                                        tv_item_title.text = it.name
                                        tv_item_rating.text = it.rating.toString()
                                    },
                                    adapterGamesCallback,
                                    {
                                        this@apply.root.findNavController().navigate(
                                            HomeFragmentDirections.actionNavHomeToNavDetail(this)
                                        )
                                    })

                                ltLoading.visibility = View.GONE
                            }
                            is Resource.Error -> {
                                ltLoading.visibility = View.GONE
                                viewError.visibility = View.VISIBLE
                            }
                        }
                    }
                })
        }
    }
}