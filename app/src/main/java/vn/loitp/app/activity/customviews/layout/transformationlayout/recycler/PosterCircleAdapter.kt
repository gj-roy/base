package vn.loitp.app.activity.customviews.layout.transformationlayout.recycler

import android.annotation.SuppressLint
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.core.adapter.BaseAdapter
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.item_poster_circle.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.TransformationDetailActivity

class PosterCircleAdapter : BaseAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(inflater.inflate(R.layout.item_poster_circle, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder) {
            holder.bind(poster = listPoster[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPosterList(list: List<Poster>) {
        listPoster.clear()
        listPoster.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listPoster.size

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(poster: Poster) {
            itemView.run {
                LImageUtil.load(
                    context = context,
                    any = poster.poster,
                    imageView = ivItemPosterPost
                )

                tvItemPosterTitle.text = poster.name
                tvItemPosterRunningTime.text = poster.playtime

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= layoutItemPosterCircleTransformation.duration)
                        TransformationDetailActivity.startActivity(
                            context,
                            layoutItemPosterCircleTransformation,
                            poster
                        )
                    previousTime = now
                }
            }
        }
    }
}
