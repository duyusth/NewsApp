package com.example.newsapp.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.newsapp.R

class ClipsAdapter(private val videos: List<Video>) : RecyclerView.Adapter<ClipsAdapter.ClipViewHolder>() {

    private var currentPlayingPosition: Int = -1
    private var currentPlayer: ExoPlayer? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_clip, parent, false)
        return ClipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClipViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

    override fun onViewDetachedFromWindow(holder: ClipViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.releasePlayer()
    }

    inner class ClipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val playerView: PlayerView = itemView.findViewById(R.id.playerView)
        private var exoPlayer: ExoPlayer? = null

        fun bind(video: Video) {
            exoPlayer = ExoPlayer.Builder(itemView.context).build().also { player ->
                playerView.player = player
                // Lấy video HD (hoặc SD nếu cần)
                val videoUrl = video.video_files.firstOrNull { it.quality == "hd" }?.link
                val mediaItem = MediaItem.fromUri(videoUrl!!)
                player.setMediaItem(mediaItem)
                player.prepare()

                if (adapterPosition == currentPlayingPosition) {
                    player.playWhenReady = true
                    currentPlayer = player
                } else {
                    player.playWhenReady = false
                }
            }
        }

        fun releasePlayer() {
            exoPlayer?.release()
            exoPlayer = null
        }
    }

    fun playVideoAtPosition(position: Int) {
        currentPlayingPosition = position
        notifyDataSetChanged() // Cập nhật lại UI để chỉ phát video tại vị trí này
    }

    fun stopCurrentPlayingVideo() {
        currentPlayer?.pause()
    }
}
