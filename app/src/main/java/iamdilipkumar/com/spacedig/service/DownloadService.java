package iamdilipkumar.com.spacedig.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import iamdilipkumar.com.spacedig.models.neo.NearEarthObject;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.utils.ApiInterface;
import iamdilipkumar.com.spacedig.utils.CommonUtils;
import iamdilipkumar.com.spacedig.utils.NetworkUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created on 29/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class DownloadService extends IntentService {

    private static final String TAG = "DownloadService";
    private CompositeDisposable mCompositeDisposable;

    public DownloadService() {
        super("downloadservice");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        mCompositeDisposable = new CompositeDisposable();

        ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);

        mCompositeDisposable.add(apiInterface.getNeoData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::apiNeoResponse, this::apiError));

    }

    private void apiNeoResponse(Neo neo) {
        if (neo.getNearEarthObjects() != null) {
            for (NearEarthObject item : neo.getNearEarthObjects()) {
                CommonUtils.getNeoModel(item, this); // Add to database
            }
        }
    }

    private void apiError(Throwable throwable) {
        Log.e(TAG, "error: " + throwable.getLocalizedMessage());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeDisposable.clear();
    }
}
