package iamdilipkumar.com.spacedig.ui.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.MediaOptions;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.ui.activities.GeneralDetailActivity;
import iamdilipkumar.com.spacedig.ui.activities.GeneralListActivity;
import iamdilipkumar.com.spacedig.utils.ParsingUtils;

/**
 * A fragment representing a single GeneralItem detail screen.
 * This fragment is either contained in a {@link GeneralListActivity}
 * in two-pane mode (on tablets) or a {@link GeneralDetailActivity}
 * on handsets.
 */
public class GeneralDetailFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView title;

    @BindView(R.id.iv_main_content_image)
    ImageView mainImage;

    @BindView(R.id.video_view)
    SimpleExoPlayerView exoPlayerView;

    @BindView(R.id.tv_description)
    TextView description;

    @BindView(R.id.tv_full_screen)
    TextView fullScreen;

    @OnClick(R.id.tv_full_screen)
    void fullScreenMode() {
        Bundle bundle = new Bundle();

        if (mMediaOptions != null) {
            bundle.putString(FullAssetFragment.FULL_VIDEO, mMediaOptions.getSmall());
        } else {
            bundle.putString(FullAssetFragment.FULL_IMAGE, mSimpleItemModel.getImageUrl());
        }

        FullAssetFragment fullAssetFragment = new FullAssetFragment();
        fullAssetFragment.setArguments(bundle);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.full_asset_container, fullAssetFragment)
                .addToBackStack(null)
                .commit();
    }

    private SimpleItemModel mSimpleItemModel;
    private MediaOptions mMediaOptions;

    SimpleExoPlayer player;
    long playbackPosition = 0L;
    int currentWindow = 1;
    boolean playWhenReady = true;

    public static final String PLAYBACK = "playback";

    public GeneralDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(GeneralDetailActivity.ITEM_DETAILS)) {
            mSimpleItemModel = getArguments().getParcelable(GeneralDetailActivity.ITEM_DETAILS);
        }

        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK);
            mSimpleItemModel = savedInstanceState.getParcelable(GeneralDetailActivity.ITEM_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_general_item_detail, container, false);

        ButterKnife.bind(this, view);

        title.setText(mSimpleItemModel.getName());
        description.setText(mSimpleItemModel.getInformation());

        String videoDownloadUrl = mSimpleItemModel.getVideoDownloadUrl();

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        exoPlayerView.setPlayer(player);

        if (videoDownloadUrl != null) {
            if (!videoDownloadUrl.isEmpty()) {
                mainImage.setVisibility(View.GONE);
                exoPlayerView.setVisibility(View.VISIBLE);

                orientationalChanges(view);

                player.setPlayWhenReady(playWhenReady);

                if (mMediaOptions == null) {
                    HttpGetRequest httpGetRequest = new HttpGetRequest();
                    httpGetRequest.execute(videoDownloadUrl);
                } else {
                    initializePlayer(mMediaOptions.getSmall());
                }
            }
        } else {
            Glide.with(this).load(mSimpleItemModel.getImageUrl())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.space_dig_main)
                    .crossFade(600)
                    .into(mainImage);
        }

        return view;
    }

    private class HttpGetRequest extends AsyncTask<String, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String result = null, inputLine;

            try {
                URL myUrl = new URL(url);

                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();
                connection.disconnect();

                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("parsing",result);
            mMediaOptions = ParsingUtils.parseMediaAsset(result);
            fullScreen.setVisibility(View.GONE);
            initializePlayer(mMediaOptions.getSmall());
        }
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

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private void orientationalChanges(View view) {
        if ((getActivity().getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE)) {
            view.findViewById(R.id.scroll_content).setVisibility(View.GONE);

            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            exoPlayerView.getLayoutParams().height = metrics.heightPixels;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (player != null) {
            player.seekTo(playbackPosition);
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong(PLAYBACK, playbackPosition);
        outState.putParcelable(GeneralDetailActivity.ITEM_DETAILS, mSimpleItemModel);
        super.onSaveInstanceState(outState);
    }
}
