package vn.loitp.app.activity.demo.chromecast.browser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.SessionManagerListener;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.chromecast.mediaplayer.LocalPlayerActivity;
import vn.loitp.app.activity.demo.chromecast.utils.Utils;
import vn.loitp.core.base.BaseFragment;

public class VideoBrowserFragment extends BaseFragment implements VideoListAdapter.ItemClickListener,
        LoaderManager.LoaderCallbacks<List<MediaInfo>> {
    private final String TAG = VideoBrowserFragment.class.getSimpleName();
    private static final String CATALOG_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/CastVideos/f.json";
    private RecyclerView mRecyclerView;
    private VideoListAdapter mAdapter;
    private View mEmptyView;
    private View mLoadingView;
    private final SessionManagerListener<CastSession> mSessionManagerListener = new MySessionManagerListener();

    @Override
    protected int setLayoutResourceId() {
        return R.layout.video_browser_fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.list);
        mEmptyView = getView().findViewById(R.id.empty_view);
        mLoadingView = getView().findViewById(R.id.progress_indicator);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new VideoListAdapter(this, getContext());
        mRecyclerView.setAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void itemClicked(View view, MediaInfo item, int position) {
        if (view instanceof ImageButton) {
            Utils.showQueuePopup(getActivity(), view, item);
        } else {
            String transitionName = getString(R.string.transition_image);
            VideoListAdapter.ViewHolder viewHolder = (VideoListAdapter.ViewHolder) mRecyclerView.findViewHolderForPosition(position);
            Pair<View, String> imagePair = Pair.create((View) viewHolder.getImageView(), transitionName);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), imagePair);
            Intent intent = new Intent(getActivity(), LocalPlayerActivity.class);
            intent.putExtra("media", item);
            intent.putExtra("shouldStart", false);
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    }

    @Override
    public Loader<List<MediaInfo>> onCreateLoader(int id, Bundle args) {
        return new VideoItemLoader(getActivity(), CATALOG_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaInfo>> loader, List<MediaInfo> data) {
        mAdapter.setData(data);
        mLoadingView.setVisibility(View.GONE);
        mEmptyView.setVisibility(null == data || data.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<MediaInfo>> loader) {
        mAdapter.setData(null);
    }

    @Override
    public void onStart() {
        CastContext.getSharedInstance(getContext()).getSessionManager().addSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onStart();
    }

    @Override
    public void onStop() {
        CastContext.getSharedInstance(getContext()).getSessionManager().removeSessionManagerListener(mSessionManagerListener, CastSession.class);
        super.onStop();
    }

    private class MySessionManagerListener implements SessionManagerListener<CastSession> {
        @Override
        public void onSessionEnded(CastSession session, int error) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSessionResumed(CastSession session, boolean wasSuspended) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSessionStarted(CastSession session, String sessionId) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public void onSessionStarting(CastSession session) {
        }

        @Override
        public void onSessionStartFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionEnding(CastSession session) {
        }

        @Override
        public void onSessionResuming(CastSession session, String sessionId) {
        }

        @Override
        public void onSessionResumeFailed(CastSession session, int error) {
        }

        @Override
        public void onSessionSuspended(CastSession session, int reason) {
        }
    }
}
