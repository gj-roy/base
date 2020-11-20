/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.recycler

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.core.adapter.AnimationAdapter
import kotlinx.android.synthetic.main.item_transformation_poster.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.DetailActivity

class PosterAdapter : AnimationAdapter() {

    private val listPoster = mutableListOf<Poster>()
    private var previousTime = SystemClock.elapsedRealtime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PosterViewHolder(inflater.inflate(R.layout.item_transformation_poster, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PosterViewHolder) {
            holder.bind(listPoster[position])
        }
    }

    fun addPosterList(list: List<Poster>) {
        listPoster.clear()
        listPoster.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount() = listPoster.size

    inner class PosterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(poster: Poster) {

            itemView.run {
                Glide.with(context)
                        .load(poster.poster)
                        .into(ivItemPosterPost)

                tvItemPosterTitle.text = poster.name
                tvItemPosterRunningTime.text = poster.playtime

                setOnClickListener {
                    val now = SystemClock.elapsedRealtime()
                    if (now - previousTime >= layoutItemPosterTransformation.duration) {
                        DetailActivity.startActivity(context, layoutItemPosterTransformation, poster)
                        previousTime = now
                    }
                }
            }

            setAnimation(viewToAnimate = itemView, position = bindingAdapterPosition)
        }
    }
}
