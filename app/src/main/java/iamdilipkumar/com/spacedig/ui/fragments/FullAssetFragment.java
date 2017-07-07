package iamdilipkumar.com.spacedig.ui.fragments;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import iamdilipkumar.com.spacedig.R;

/**
 * Created on 04/07/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class FullAssetFragment extends Fragment {

    @BindView(R.id.iv_full_screen)
    ImageView ivFull;

    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;

    SimpleExoPlayer player;
    long playbackPosition = 0L;
    int currentWindow = 1;
    boolean playWhenReady = true;

    public static final String FULL_IMAGE = "fullimage";
    public static final String FULL_VIDEO = "fullvideo";

    public FullAssetFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext())
                    .inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_asset, container, false);

        ButterKnife.bind(this, view);

        if (getArguments().containsKey(FULL_IMAGE)) {
            String imageUrl = getArguments().getString(FULL_IMAGE);

            Glide.with(this).load(imageUrl)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.space_dig_main)
                    .into(ivFull);

            exoPlayerView.setVisibility(View.INVISIBLE);
        }

        if (getArguments().containsKey(FULL_VIDEO)) {
            ivFull.setVisibility(View.INVISIBLE);
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            exoPlayerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            String video = getArguments().getString(FULL_VIDEO);
            Log.d("video", video);
            initializePlayer(video);
            //player.seekTo(currentWindow, playbackPosition);
        }

        return view;
    }

    private void initializePlayer(String videoUrl) {
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
}
