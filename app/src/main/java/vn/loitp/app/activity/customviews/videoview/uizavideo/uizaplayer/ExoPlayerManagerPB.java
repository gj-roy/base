/*
 * Copyright 2016 The Android Open Source Project
 * Copyright 2017 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.loitp.app.activity.customviews.videoview.uizavideo.uizaplayer;

import android.net.Uri;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.request.target.Target;
import com.github.rubensousa.previewseekbar.base.PreviewLoader;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.ads.interactivemedia.v3.api.player.VideoAdPlayer;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.ima.ImaAdsLoader;
import com.google.android.exoplayer2.ext.ima.ImaAdsMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;

import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.glide.GlideApp;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.glide.GlideThumbnailTransformationPB;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.AudioEventListener;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.MetadataOutputListener;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.PlayerEventListener;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.TextOutputListener;
import vn.loitp.app.activity.customviews.videoview.uizavideo.listerner.VideoEventListener;
import vn.loitp.core.utilities.LLog;

public class ExoPlayerManagerPB implements PreviewLoader {
    private final String TAG = getClass().getSimpleName();
    private ExoPlayerMediaSourceBuilderPB mediaSourceBuilder;
    private SimpleExoPlayerView playerView;
    private SimpleExoPlayer player;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private String thumbnailsUrl;
    private ImageView imageView;

    private Player.EventListener eventListener = new Player.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (playbackState == Player.STATE_READY && playWhenReady) {
                if (previewTimeBarLayout != null) {
                    previewTimeBarLayout.hidePreview();
                }
            }
        }
    };

    public ExoPlayerManagerPB(SimpleExoPlayerView playerView, PreviewTimeBarLayout previewTimeBarLayout, ImageView imageView, String thumbnailsUrl) {
        this.playerView = playerView;
        this.imageView = imageView;
        this.previewTimeBarLayout = previewTimeBarLayout;
        this.mediaSourceBuilder = new ExoPlayerMediaSourceBuilderPB(playerView.getContext());
        this.thumbnailsUrl = thumbnailsUrl;
    }

    public void play(Uri uri) {
        mediaSourceBuilder.setUri(uri);
    }

    public void onStart() {
        if (Util.SDK_INT > 23) {
            createPlayers();
        }
    }

    public void onResume() {
        if (Util.SDK_INT <= 23) {
            createPlayers();
        }
    }

    public void onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayers();
        }
    }

    public void onStop() {
        contentPosition = player.getContentPosition();
        if (Util.SDK_INT > 23) {
            releasePlayers();
        }
    }

    public void onDestroy() {

        //TODO
        /*if (imaAdsLoader != null) {
            imaAdsLoader.release();
        }*/
    }

    public void stopPreview() {
        player.setPlayWhenReady(true);
    }

    private void releasePlayers() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void createPlayers() {
        if (player != null) {
            player.release();
        }
        player = createFullPlayer();
        playerView.setPlayer(player);
    }

    private SimpleExoPlayer createFullPlayer() {
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(playerView.getContext()), trackSelector, loadControl);
        player.setPlayWhenReady(true);

        MediaSource mediaSource = mediaSourceBuilder.getMediaSource(false);
        player.prepare(mediaSource);

        player.addListener(eventListener);
        player.addListener(new PlayerEventListener());
        player.addAudioDebugListener(new AudioEventListener());
        player.addVideoDebugListener(new VideoEventListener());
        player.addMetadataOutput(new MetadataOutputListener());
        player.addTextOutput(new TextOutputListener());


        /*MediaSource mediaSourceWithAds = new ImaAdsMediaSource(
                mediaSource,
                null,
                imaAdsLoader,
                playerView.getOverlayFrameLayout());
        //player.seekTo(contentPosition);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);*/

        /*String urlImaAd = "https://pubads.g.doubleclick.net/gampad/ads?sz=640x480&iu=/124319096/external/ad_rule_samples&ciu_szs=300x250&ad_rule=1&impl=s&gdfp_req=1&env=vp&output=vmap&unviewed_position_start=1&cust_params=deployment%3Ddevsite%26sample_ar%3Dpreonly&cmsid=496&vid=short_onecue&correlator=";
        ImaAdsLoader imaAdsLoader = new ImaAdsLoader(playerView.getContext(), Uri.parse(urlImaAd));

        FrameLayout adOverlayViewGroup = new FrameLayout(playerView.getContext());
        playerView.getOverlayFrameLayout().addView(adOverlayViewGroup);
        //ImaAdsMediaSource imaAdsMediaSource = new ImaAdsMediaSource(mediaSource, null, imaAdsLoader, adOverlayViewGroup);
        ImaAdsMediaSource imaAdsMediaSource = new ImaAdsMediaSource(mediaSource, mediaSourceBuilder.getDataSourceFactory(false), imaAdsLoader, adOverlayViewGroup, new Handler(), null);

        player.prepare(imaAdsMediaSource);
        player.setPlayWhenReady(true);*/

        return player;
    }

    private long contentPosition = 0;

    @Override
    public void loadPreview(long currentPosition, long max) {
        player.setPlayWhenReady(false);
        GlideApp.with(imageView)
                .load(thumbnailsUrl)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .transform(new GlideThumbnailTransformationPB(currentPosition))
                .into(imageView);

    }
}
