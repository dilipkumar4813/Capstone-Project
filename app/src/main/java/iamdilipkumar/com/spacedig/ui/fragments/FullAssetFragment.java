package iamdilipkumar.com.spacedig.ui.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_asset, container, false);

        ButterKnife.bind(this, view);

        if (getArguments().containsKey(FULL_IMAGE)) {
            String imageUrl = getArguments().getString(FULL_IMAGE);
            Picasso.with(getContext()).load(imageUrl).into(ivFull);
            exoPlayerView.setVisibility(View.INVISIBLE);
        }

        if (getArguments().containsKey(FULL_VIDEO)) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            exoPlayerView.setPlayer(player);

            player.setPlayWhenReady(playWhenReady);
            //player.seekTo(currentWindow, playbackPosition);
            initializePlayer();
        }

        HttpGetRequest getRequest = new HttpGetRequest();
        getRequest.execute();

        return view;
    }

    private void initializePlayer() {
        Uri uri = Uri.parse("https://images-assets.nasa.gov/video/NHQ_2017_0523_FY18%20State%20Of%20NASA%20Budget/NHQ_2017_0523_FY18%20State%20Of%20NASA%20Budget~orig.mp4");
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

    public class HttpGetRequest extends AsyncTask<String, Void, String> {

        static final String REQUEST_METHOD = "GET";
        static final int READ_TIMEOUT = 15000;
        static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params) {
            String url = "https://images-assets.nasa.gov/video/NHQ_2017_0523_FY18%20State%20Of%20NASA%20Budget/collection.json";
            String result = null, inputLine;

            URL myUrl = null;
            try {
                myUrl = new URL(url);

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

                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("results", result);
        }
    }
}
