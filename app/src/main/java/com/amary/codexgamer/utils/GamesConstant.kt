package com.amary.codexgamer.utils

import androidx.recyclerview.widget.DiffUtil
import com.amary.codexgamer.domain.model.Games
import com.amary.codexgamer.domain.model.GamesFavorite

object GamesConstant {

    val adapterGamesCallback = object : DiffUtil.ItemCallback<Games>() {
        override fun areItemsTheSame(oldItem: Games, newItem: Games) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Games, newItem: Games) = oldItem == newItem
    }

    val adapterScreenShotCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }

    val adapterFavoriteGamesCallback: DiffUtil.ItemCallback<GamesFavorite> = object : DiffUtil.ItemCallback<GamesFavorite>() {
        override fun areItemsTheSame(oldItem: GamesFavorite, newItem: GamesFavorite) =
            oldItem.favorite == newItem.favorite

        override fun areContentsTheSame(oldItem: GamesFavorite, newItem: GamesFavorite) =
            oldItem == newItem
    }
}