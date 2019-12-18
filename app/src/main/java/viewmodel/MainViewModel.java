package viewmodel;

import android.app.Application;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.countries.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.Country;
import model.Status;
import network.CountryRepository;

public class MainViewModel extends AndroidViewModel {
    private CountryRepository repository;
    private final ObservableField<Status> responseLiveData = new ObservableField<>();
    private  MutableLiveData<List<Country>> countries = new MutableLiveData<List<Country>>();
    ObservableInt isAsc = new ObservableInt(1);
    ObservableBoolean isCountry = new ObservableBoolean(true);

    public ObservableInt getIsAsc() {
        return isAsc;
    }

    public void setIsAsc(int isAsc) {
        this.isAsc.set(isAsc);
        if (countries.getValue()!=null) {
            Collections.reverse(countries.getValue());
            countries.setValue(countries.getValue());
        }

    }

    public ObservableBoolean getIsCountry() {
        return isCountry;
    }

    public void setIsCountry(boolean isCountry) {
        this.isCountry .set(isCountry);
        if (countries.getValue()==null)
            return;
        if (!isCountry){
            Observable.fromIterable(countries.getValue())
                    .toSortedList((rowModel, rowModel2) -> {
                        if (Integer.parseInt( rowModel.getPopulation()) < Integer.parseInt(rowModel2.getPopulation())) {
                            return -1*isAsc.get();
                        } else if (rowModel.getPopulation()== rowModel2.getPopulation()) {
                            return 0;
                        } else {
                            return 1*isAsc.get();
                        }

                    })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list ->countries.setValue(list));

        }else{
            Observable.fromIterable(countries.getValue())
                    .toSortedList((rowModel, rowModel2) -> {
                        int res = String.CASE_INSENSITIVE_ORDER.compare(rowModel.getName(), rowModel2.getName());
                        return res*isAsc.get();
                    })
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list ->countries.setValue(list));

        }
    }

    public ObservableField<Status> response() {
        return responseLiveData;
    }


    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new CountryRepository();
        responseLiveData.set(Status.LOADING);
    }


    public LiveData<List<Country>> getAllCountries() {
        responseLiveData.set(Status.LOADING);
         countries =  repository.getMutableLiveData();
         return  countries;
    }

    public LiveData<List<Country>> filter(Country country) {
        LiveData<List<Country>> filterCountries = new MutableLiveData<List<Country>>();
        io.reactivex.Observable
                .fromIterable(countries.getValue())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(sub-> responseLiveData.set(Status.LOADING))
                .subscribeOn(Schedulers.computation())
                .filter(model ->{
                    for (String border:country.getBorders()) {
                        if (model.getAlpha3Code().toUpperCase().startsWith(border.toUpperCase()))
                            return true;
                    }
                   return false;
                } )
                .toList()
                .subscribe(new SingleObserver<List<Country>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        /* to do */
                    }

                    @Override
                    public void onSuccess(List<Country> countryList) {
                        responseLiveData.set(Status.SUCCESS);
                        ((MutableLiveData<List<Country>>) filterCountries).setValue(countryList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        responseLiveData.set(Status.ERROR);

                    }
                });

        return filterCountries;
    }
}
