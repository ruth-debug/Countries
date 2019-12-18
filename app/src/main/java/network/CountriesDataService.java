package network;

import java.util.ArrayList;

import io.reactivex.Observable;
import model.Country;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CountriesDataService {
    @GET("all?fields=name;nativeName;population;borders;alpha3Code")
    Call<ArrayList<Country>> getAllCountries();




}
