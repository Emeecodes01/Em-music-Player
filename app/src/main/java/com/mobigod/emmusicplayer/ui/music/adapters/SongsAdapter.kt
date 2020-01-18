package com.mobigod.emmusicplayer.ui.music.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mobigod.emmusicplayer.R
import com.mobigod.emmusicplayer.data.model.Song
import com.mobigod.emmusicplayer.databinding.SongItemLayoutBinding

class SongsAdapter: RecyclerView.Adapter<SongsAdapter.SongsViewHolder>() {
    private val songList: MutableList<Song> = mutableListOf()
    lateinit var binding: SongItemLayoutBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsViewHolder {
        binding = SongItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SongsViewHolder(binding.root)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindToView(position)
    }


    fun addSongs(songs: List<Song>) {
        songList.addAll(songs)
        notifyDataSetChanged()
    }


    fun clearSongs() {
        songList.clear()
        notifyDataSetChanged()
    }


    inner class SongsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindToView(position: Int) {
            binding.songDisplayName.text = songList[position].title.trim()
            binding.artistName.text = songList[position].artist.trim()

            binding.albumArt.clipToOutline = true

            Glide.with(itemView)
                .load(songList[position].albumArt)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.ic_headphones)
                .error(R.drawable.ic_headphones)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.albumArt)


        }
    }

}