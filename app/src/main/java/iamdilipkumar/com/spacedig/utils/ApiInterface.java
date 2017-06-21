package iamdilipkumar.com.spacedig.utils;

import iamdilipkumar.com.spacedig.models.Apod;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created on 21/06/17.
 *
 * @author dilipkumar4813
 * @version 1.0
 */

public interface ApiInterface {

    @GET("planetary/apod")
    Observable<Apod> getApod();
}
