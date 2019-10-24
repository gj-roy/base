package vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LPopupMenu;
import com.core.utilities.LUIUtil;
import com.views.LToast;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie;
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter;
import vn.loitp.app.common.Constants;

public class RecyclerViewWithSingletonDataActivity extends BaseFontActivity {
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private TextView tvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.rv);
        tvType = findViewById(R.id.tv_type);

        mAdapter = new MoviesAdapter(getActivity(), DummyData.Companion.getInstance().getMovieList(), new MoviesAdapter.Callback() {
            @Override
            public void onClick(Movie movie, int position) {
                LToast.show(getActivity(), "Click " + movie.getTitle());
            }

            @Override
            public void onLongClick(Movie movie, int position) {
                boolean isRemoved = DummyData.Companion.getInstance().getMovieList().remove(movie);
                if (isRemoved) {
                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemRangeChanged(position, DummyData.Companion.getInstance().getMovieList().size());
                }
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        LUIUtil.INSTANCE.setPullLikeIOSVertical(recyclerView);

        prepareMovieData();

        findViewById(R.id.bt_setting).setOnClickListener(v -> LPopupMenu.INSTANCE.show(getActivity(), v, R.menu.menu_recycler_view, menuItem -> {
            tvType.setText(menuItem.getTitle().toString());
            switch (menuItem.getItemId()) {
                case R.id.menu_linear_vertical:
                    RecyclerView.LayoutManager lmVertical = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(lmVertical);
                    break;
                case R.id.menu_linear_horizontal:
                    RecyclerView.LayoutManager lmHorizontal = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(lmHorizontal);
                    break;
                case R.id.menu_gridlayoutmanager_2:
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    break;
                case R.id.menu_gridlayoutmanager_3:
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    break;
                case R.id.menu_staggeredgridlayoutmanager_2:
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    break;
                case R.id.menu_staggeredgridlayoutmanager_4:
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL));
                    break;
            }
        }));
    }

    private void loadMore() {
        LLog.d(getTAG(), "loadMore");
        LUIUtil.INSTANCE.setDelay(2000, new Runnable() {
            @Override
            public void run() {
                int newSize = 5;
                for (int i = 0; i < newSize; i++) {
                    Movie movie = new Movie("Add new " + i, "Add new " + i, "Add new: " + i, Constants.INSTANCE.getURL_IMG());
                    DummyData.Companion.getInstance().getMovieList().add(movie);
                }
                mAdapter.notifyDataSetChanged();
                LToast.show(getActivity(), "Finish loadMore");
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_recycler_view;
    }

    private void prepareMovieData() {
        if (DummyData.Companion.getInstance().getMovieList().isEmpty()) {
            for (int i = 0; i < 10; i++) {
                Movie movie = new Movie("Loitp " + i, "Action & Adventure " + i, "Year: " + i, Constants.INSTANCE.getURL_IMG());
                DummyData.Companion.getInstance().getMovieList().add(movie);
            }
        }
        mAdapter.notifyDataSetChanged();
    }
}
