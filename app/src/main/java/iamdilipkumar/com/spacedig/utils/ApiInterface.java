package iamdilipkumar.com.spacedig.utils;

import java.util.List;

import iamdilipkumar.com.spacedig.models.Apod;
import iamdilipkumar.com.spacedig.models.epic.Epic;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.models.rover.MarsRover;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

    @GET("/EPIC/api/natural/date/{date}")
    Observable<List <Epic>> getEpicData(@Path("date") String queryDate);

    @GET("/neo/rest/v1/neo/browse")
    Observable<Neo> getNeoData();
}
