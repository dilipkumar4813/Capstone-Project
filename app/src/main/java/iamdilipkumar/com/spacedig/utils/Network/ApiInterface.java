package iamdilipkumar.com.spacedig.utils.Network;

import java.util.List;

import iamdilipkumar.com.spacedig.models.Apod;
import iamdilipkumar.com.spacedig.models.EarthImagery;
import iamdilipkumar.com.spacedig.models.cad.Cad;
import iamdilipkumar.com.spacedig.models.search.Search;
import iamdilipkumar.com.spacedig.models.epic.Epic;
import iamdilipkumar.com.spacedig.models.neo.Neo;
import iamdilipkumar.com.spacedig.models.rover.MarsRover;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

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
    Observable<List<Epic>> getEpicData(@Path("date") String queryDate);

    @GET("/neo/rest/v1/neo/browse")
    Observable<Neo> getNeoData();

    // Example lon=77.6309395&lat=12.9539974&date=2014-02-01
    @GET("/planetary/earth/imagery")
    Observable<EarthImagery> getEarthImage(@Query("lon") String longitude,
                                           @Query("lat") String latitude,
                                           @Query("date") String queryDate);

    // Without the API key
    @GET("/search")
    Observable<Search> getSearchData(@Query("q") String searchTerm);

    // CAD API no key required
    @GET("/cad.api")
    Observable<Cad> getCadData(@Query("dist-max") String distance,
                               @Query("date-min") String dateMin,
                               @Query("sort") String sort);
}
