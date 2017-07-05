package iamdilipkumar.com.spacedig.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.data.NasaProvider;
import iamdilipkumar.com.spacedig.models.cad.Cad;
import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.utils.parsing.CadUtils;
import iamdilipkumar.com.spacedig.utils.parsing.NeoUtils;
import iamdilipkumar.com.spacedig.utils.Network.ApiInterface;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.Network.NetworkUtils;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mainList = new Intent(this, SpaceListActivity.class);
        mCompositeDisposable = new CompositeDisposable();

        if (CommonUtils.checkNetworkConnectivity(this)) {
            downloadData();
        } else {
            loadScreen();
        }
    }

    /**
     * Method to download NEO and CAD data
     * Insert the downloaded data into SQLite
     */
    private void downloadData() {
        ApiInterface apiNeoInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);

        mCompositeDisposable.add(apiNeoInterface.getNeoData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiNeoResponse, this::apiError));

        ApiInterface apiCadInterface = NetworkUtils.buildCadRetrofit().create(ApiInterface.class);

        // Load correct model
        mCompositeDisposable.add(apiCadInterface.getCadData("10LD", "2017-01-01", "dist")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiCadResponse, this::apiError));
    }

    /**
     * Method to parse Neo Object into Content provider
     *
     * @param neo - Object received from retrofit response
     */
    private void apiNeoResponse(Neo neo) {
        if (neo.getNearEarthObjects() != null) {

            getContentResolver().delete(NasaProvider.NeoData.CONTENT_URI, null, null);

            for (NearEarthObject item : neo.getNearEarthObjects()) {
                NeoUtils.getNeoModel(item, this);
            }
        }
    }

    private void apiCadResponse(Cad cad) {
        if (cad != null) {
            getContentResolver().delete(NasaProvider.CadData.CONTENT_URI, null, null);

            CadUtils.getCadDataIntoContentProvider(cad, this);
        }

        loadScreen();
    }

    /**
     * Method to print the error response
     *
     * @param throwable - Message to be displayed on the monitor
     */
    private void apiError(Throwable throwable) {
        Log.e(TAG, "error: " + throwable.getLocalizedMessage());
        loadScreen();
    }

    /**
     * Method to load the main listing screen
     */
    private void loadScreen() {
        SplashActivity.this.finish();
        startActivity(mainList);
    }

    /**
     * Clear composite disposable while destroying the activity
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
