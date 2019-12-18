package view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.test.countries.R;
import com.test.countries.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import handler.ItemClickListener;
import model.Status;
import view.custom.HeaderDecoration;
import model.Country;
import view.adapter.CountriesAdapter;
import viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private CountriesAdapter countryAdapter;
    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         activityMainBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);

        // bind RecyclerView
        RecyclerView recyclerView = activityMainBinding.viewCountries;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //divider
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

      /*  //header
        LayoutInflater factory = LayoutInflater.from(this);
        View headerView = factory.inflate(R.layout.header_view, null);
        HeaderDecoration headerDecoration = new HeaderDecoration(headerView,false,0.3f,0.5f,2);
*///        recyclerView.addItemDecoration(headerDecoration);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityMainBinding.setMyViewModel(mainViewModel);
        countryAdapter = new CountriesAdapter();
        countryAdapter.setItemClickListener((v, country) -> {
            mainViewModel.filter(country).observe(this, countries -> {
                mainViewModel.response().set(Status.SUCCESS);
                v.getContext().startActivity(new Intent(v.getContext(), CuntryDetailsActivity.class).putParcelableArrayListExtra("borders",(ArrayList<Country>) countries));
            });;
        });
        recyclerView.setAdapter(countryAdapter);
        getAllCountries();

        activityMainBinding.radioSort.setOnCheckedChangeListener((radioGroup, i) -> {
            mainViewModel.setIsAsc(mainViewModel.getIsAsc().get()*-1);
        });
        activityMainBinding.radioType.setOnCheckedChangeListener((radioGroup, i) -> {
            mainViewModel.setIsCountry(!mainViewModel.getIsCountry().get());
        });
    }
    private void getAllCountries() {
        mainViewModel.getAllCountries().observe(this, countries -> {
           mainViewModel.response().set(Status.SUCCESS);
            countryAdapter.setList((ArrayList<Country>) countries);
        });

    }

}
