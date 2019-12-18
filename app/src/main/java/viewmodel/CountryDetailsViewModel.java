package viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import java.util.List;


import model.Country;
import model.Status;
import network.CountryRepository;

public class CountryDetailsViewModel  extends AndroidViewModel {
    private CountryRepository repository;
    private final ObservableField<Status> responseLiveData = new ObservableField<>();
    private MutableLiveData<List<Country>> countries = new MutableLiveData<List<Country>>();





    public ObservableField<Status> response() {
        return responseLiveData;
    }


    public CountryDetailsViewModel(@NonNull Application application) {
        super(application);
        repository = new CountryRepository();
        responseLiveData.set(Status.LOADING);
        countries = new MutableLiveData<>();
    }

    public LiveData<List<Country>> getAllCountries(List<Country>list) {
        countries.setValue(list);
        return  countries;
    }
}
