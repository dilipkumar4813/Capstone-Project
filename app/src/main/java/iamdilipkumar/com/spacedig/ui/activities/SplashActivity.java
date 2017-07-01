package iamdilipkumar.com.spacedig.ui.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.data.NeoColumns;
import iamdilipkumar.com.spacedig.models.SimpleItemModel;
import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.service.DownloadService;
import iamdilipkumar.com.spacedig.utils.ApiInterface;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 27/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";
    private CompositeDisposable mCompositeDisposable;
    private Intent mainList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mainList = new Intent(this, SpaceListActivity.class);
        mCompositeDisposable = new CompositeDisposable();

        if (CommonUtils.checkNetworkConnectivity(this)) {
            downloadData();
        }else{
            loadScreen();
        }
    }

    private void downloadData() {
        ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);

        mCompositeDisposable.add(apiInterface.getNeoData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiNeoResponse, this::apiError));
    }

    private void apiNeoResponse(Neo neo) {
        if (neo.getNearEarthObjects() != null) {
            getContentResolver().delete(NasaProvider.NeoData.CONTENT_URI, null, null);
            for (NearEarthObject item : neo.getNearEarthObjects()) {
                SimpleItemModel insertModel = CommonUtils.getNeoModel(item, this); // Add to database

                ContentValues neoData = new ContentValues();
                neoData.put(NeoColumns.NEO_ID, insertModel.getId());
                neoData.put(NeoColumns.NAME, insertModel.getName());
                neoData.put(NeoColumns.DESCRIPTION, insertModel.getInformation());
                neoData.put(NeoColumns.SHORT_DESCRIPTION, insertModel.getShortDescription());
                neoData.put(NeoColumns.IMAGEURL, insertModel.getImageUrl());
                getContentResolver().insert(NasaProvider.NeoData.CONTENT_URI, neoData);
            }
        }

        loadScreen();
    }

    private void apiError(Throwable throwable) {
        Log.e(TAG, "error: " + throwable.getLocalizedMessage());
        loadScreen();
    }

    private void loadScreen(){
        SplashActivity.this.finish();
        startActivity(mainList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
