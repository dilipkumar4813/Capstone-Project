package iamdilipkumar.com.spacedig.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;

import iamdilipkumar.com.spacedig.R;
import iamdilipkumar.com.spacedig.models.Apod;
import iamdilipkumar.com.spacedig.ui.activities.ApodDetailActivity;
import iamdilipkumar.com.spacedig.utils.Network.ApiInterface;
import iamdilipkumar.com.spacedig.utils.Network.NetworkUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Implementation of Apod Widget functionality.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
public class ApodWidget extends AppWidgetProvider {

    private static final String TAG = "ApodWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        ApiInterface apiInterface = NetworkUtils.buildRetrofit().create(ApiInterface.class);
        Call<Apod> call = apiInterface.getApodWidget();
        call.enqueue(new Callback<Apod>() {
            @Override
            public void onResponse(Call<Apod> call, Response<Apod> response) {
                Log.d(TAG, "Response: " + response.message());

                RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.apod_widget);

                String mainTitle = response.body().getTitle();
                String imageUrl = response.body().getUrl();

                updateViews.setTextViewText(R.id.appwidget_text, mainTitle);

                AppWidgetTarget appWidgetTarget = new AppWidgetTarget(context,
                        updateViews, R.id.iv_widget_image, appWidgetIds);
                Glide.with(context.getApplicationContext())
                        .load(imageUrl)
                        .asBitmap()
                        .into(appWidgetTarget);

                Intent intent = new Intent(context, ApodDetailActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                updateViews.setOnClickPendingIntent(R.id.iv_widget_image, pendingIntent);

                final ComponentName cn = new ComponentName(context, ApodWidget.class);
                final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                mgr.updateAppWidget(cn, updateViews);

            }

            @Override
            public void onFailure(Call<Apod> call, Throwable t) {
                Log.d(TAG, "Failure Response: " + t.getMessage());
            }
        });
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

