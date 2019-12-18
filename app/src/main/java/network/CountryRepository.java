package network;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import model.Country;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository {
    private static final String TAG = "CountryRepository";
    private ArrayList<Country> Countrys = new ArrayList<>();
    private MutableLiveData<List<Country>> mutableLiveData = new MutableLiveData<>();

    public CountryRepository() {
    }

    public MutableLiveData<List<Country>> getMutableLiveData() {

        final CountriesDataService userDataService = RetrofitClient.getInstance().countriesService;

        Call<ArrayList<Country>> call = userDataService.getAllCountries();
        call.enqueue(new Callback<ArrayList<Country>>() {
            @Override
            public void onResponse(Call<ArrayList<Country>> call, Response<ArrayList<Country>> response) {
                ArrayList<Country> CountryDBResponse = response.body();
                if (CountryDBResponse != null) {
                    Countrys = CountryDBResponse;
                }
                mutableLiveData.setValue(Countrys);
            }

            @Override
            public void onFailure(Call<ArrayList<Country>> call, Throwable t) {
                mutableLiveData.setValue(Countrys);

            }
        });

        return mutableLiveData;
    }


}
