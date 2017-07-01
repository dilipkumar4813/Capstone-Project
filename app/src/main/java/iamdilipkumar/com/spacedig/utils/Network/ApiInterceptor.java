package iamdilipkumar.com.spacedig.utils.Network;

import java.io.IOException;

import iamdilipkumar.com.spacedig.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 21/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */
class ApiInterceptor implements Interceptor {

    private final static String PARAM_API = "api_key";

    private final static String API_KEY = BuildConfig.API;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(PARAM_API, API_KEY)
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
