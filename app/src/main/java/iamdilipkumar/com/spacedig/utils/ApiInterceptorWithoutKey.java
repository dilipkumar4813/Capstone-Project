package iamdilipkumar.com.spacedig.utils;

import java.io.IOException;

import iamdilipkumar.com.spacedig.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created on 29/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public class ApiInterceptorWithoutKey implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .build();

        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
