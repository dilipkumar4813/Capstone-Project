package iamdilipkumar.com.spacedig.utils;

import java.util.List;

import iamdilipkumar.com.spacedig.models.Apod;
import iamdilipkumar.com.spacedig.models.MarsRover;
import iamdilipkumar.com.spacedig.models.MarsRoverPhoto;
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

    @GET("/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&")
    Observable<MarsRover> getRoverPhotos();
}
