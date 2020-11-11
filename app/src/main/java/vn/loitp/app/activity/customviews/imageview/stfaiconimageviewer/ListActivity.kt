package vn.loitp.app.activity.customviews.imageview.stfaiconimageviewer

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_stfaiconimageviewer_list.*
import kotlinx.android.synthetic.main.view_stf_overlay.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.common.Constants
import java.util.*

@LogTag("loitppListActivity")
@IsFullScreen(false)
class ListActivity : BaseFontActivity() {
    private val movieList: MutableList<Movie> = ArrayList()
    private var stfAdapter: StfAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_stfaiconimageviewer_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        stfAdapter = StfAdapter(
                context = this,
                moviesList = movieList,
                callback = object : StfAdapter.Callback {
                    override fun onClick(movie: Movie, mvList: MutableList<Movie>, position: Int) {

                        val viewOverLay = LayoutInflater.from(this@ListActivity).inflate(R.layout.view_stf_overlay, null)
                        viewOverLay.bt.setOnClickListener {
                            showShortInformation("Click")
                        }

                        StfalconImageViewer.Builder(
                                this@ListActivity,
                                mvList
                        ) { imageView, mv ->
                            LImageUtil.load(
                                    this@ListActivity,
                                    mv.cover,
                                    imageView
                            )
                        }
                                .withStartPosition(position)
                                .withOverlayView(viewOverLay)
                                .withImageChangeListener { pos ->
                                    logD("withImageChangeListener pos $pos")
                                }
                                .withDismissListener {
                                    logD("withDismissListener")
                                }
                                .show(true)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        val isRemoved = movieList.remove(movie)
                        if (isRemoved) {
                            stfAdapter?.apply {
                                notifyItemRemoved(position)
                                notifyItemRangeChanged(position, movieList.size)
                            }
                        }
                    }

                    override fun onLoadMore() {
                        //do nothing
                    }
                })
        rv.layoutManager = GridLayoutManager(this, 5)
        rv.adapter = stfAdapter

        prepareMovieData()
    }

    private fun prepareMovieData() {
        var cover: String
        for (i in 0..99) {
            cover = if (i % 2 == 0) {
                Constants.URL_IMG_1
            } else {
                Constants.URL_IMG_2
            }
            val movie = Movie(
                    title = "Loitp $i",
                    genre = "Action & Adventure $i",
                    year = "Year: $i",
                    cover = cover
            )
            movieList.add(movie)
        }
        stfAdapter?.notifyDataSetChanged()
    }
}
