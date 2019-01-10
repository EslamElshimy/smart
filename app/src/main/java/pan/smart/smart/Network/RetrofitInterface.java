package pan.smart.smart.Network;
import java.util.List;

import pan.smart.smart.Models.CountryModelResponse;

import retrofit2.http.GET;

import rx.Observable;


public interface RetrofitInterface
{

        @GET("all")
        Observable<List<CountryModelResponse>> getCountries();


}