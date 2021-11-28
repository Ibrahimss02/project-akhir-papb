package com.papb.projectakhirpapb.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.papb.projectakhirpapb.databinding.GameItemBinding
import com.papb.projectakhirpapb.dto.Game

class GameAdapter(private val clickListener: GameItemListener) : ListAdapter<Game, GameAdapter.GameViewHolder>(GameDiffUtil()) {

    inner class GameViewHolder(private val binding : GameItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.game = game
            binding.onClickListener = clickListener
            binding.tvJudulGame.text = game.nama
            binding.tvGenreGame.text = String.format("Genre: %s", game.genre)
            binding.tvGamePublisher.text = String.format("Publisher: %s", game.publisher)
            binding.tvRating.text = game.rating.toString()
            binding.tvGameReleaseYear.text = game.tahun_rilis.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            GameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = getItem(position)

        holder.bind(game)
    }
}

class GameItemListener(val clickListener: (game: Game) -> Unit) {
    fun onClick(game: Game) = clickListener(game)
}

class GameDiffUtil : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}