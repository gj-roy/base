package vn.loitp.app.a.cv.rv.normalRv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setDelay
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.showPopup
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator
import kotlinx.android.synthetic.main.activity_recycler_view.*
import vn.loitp.R
import vn.loitp.common.Constants

// https://github.com/wasabeef/recyclerview-animators
@LogTag("RecyclerViewActivity")
@IsFullScreen(false)
class RecyclerViewActivityFont : BaseActivityFont() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RecyclerViewActivityFont::class.java.simpleName
        }

        val animator = SlideInRightAnimator(OvershootInterpolator(1f))
        animator.addDuration = 300
        rv.itemAnimator = animator
        btAdd3.setOnClickListener {
            val movie = Movie()
            movie.title = "Add TITLE 3"
            movie.year = "Add YEAR 3"
            movie.genre = "Add GENRE 3"
            movieList.add(index = 3, element = movie)
            moviesAdapter?.notifyItemInserted(3)
        }
        btRemove1.setOnClickListener {
            movieList.removeAt(index = 1)
            moviesAdapter?.notifyItemRemoved(1)
        }
        moviesAdapter = MoviesAdapter(
            moviesList = movieList,
            callback = object : MoviesAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                    val isRemoved = movieList.remove(movie)
                    if (isRemoved) {
                        moviesAdapter?.notifyItemRemoved(position)
                        moviesAdapter?.notifyItemRangeChanged(position, movieList.size)
                    }
                }

                override fun onLoadMore() {
                    loadMore()
                }
            }
        )
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager

        moviesAdapter?.let {
            val scaleAdapter = ScaleInAnimationAdapter(it)
            scaleAdapter.setDuration(1000)
            scaleAdapter.setInterpolator(OvershootInterpolator())
            scaleAdapter.setFirstOnly(true)
            rv.adapter = scaleAdapter
        }
        prepareMovieData()
        btSetting.setOnClickListener {
            this.showPopup(
                showOnView = it,
                menuRes = R.menu.menu_recycler_view,
                callback = { menuItem ->
                    tvType.text = menuItem.title.toString()
                    when (menuItem.itemId) {
                        R.id.menuLinearVertical -> {
                            val lmVertical: RecyclerView.LayoutManager =
                                LinearLayoutManager(this@RecyclerViewActivityFont)
                            rv.layoutManager = lmVertical
                        }
                        R.id.menuLinearHorizontal -> {
                            val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(
                                this@RecyclerViewActivityFont,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            rv.layoutManager = lmHorizontal
                        }
                        R.id.menuGridLayoutManager2 ->
                            rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewActivityFont, 2)
                        R.id.menuGridLayoutManager3 ->
                            rv.layoutManager =
                                GridLayoutManager(this@RecyclerViewActivityFont, 3)
                        R.id.menuStaggeredGridLayoutManager2 ->
                            rv.layoutManager =
                                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        R.id.menuStaggeredGridLayoutManager4 ->
                            rv.layoutManager =
                                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                    }
                }
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadMore() {
        setDelay(
            mls = 2000,
            runnable = {
                val newSize = 5
                for (i in 0 until newSize) {
                    val movie = Movie(
                        title = "Add new $i",
                        genre = "Add new $i",
                        year = "Add new: $i",
                        cover = Constants.URL_IMG
                    )
                    movieList.add(movie)
                }
                moviesAdapter?.notifyDataSetChanged()
                showShortInformation("Finish loadMore")
            }
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun prepareMovieData() {
        for (i in 0..99) {
            val movie = Movie(
                title = "Loitp $i",
                genre = "Action & Adventure $i",
                year = "Year: $i",
                cover = Constants.URL_IMG
            )
            movieList.add(movie)
        }
        moviesAdapter?.notifyDataSetChanged()
    }
}
