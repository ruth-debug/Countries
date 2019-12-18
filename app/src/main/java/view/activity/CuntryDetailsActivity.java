package view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.test.countries.R;
import com.test.countries.databinding.ActivityCuntryDetailsBinding;
import com.test.countries.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import model.Country;
import model.Status;
import view.adapter.CountriesAdapter;
import viewmodel.CountryDetailsViewModel;
import viewmodel.MainViewModel;

public class CuntryDetailsActivity extends AppCompatActivity {

    private CountryDetailsViewModel viewModel;
    private CountriesAdapter countryAdapter;
    ActivityCuntryDetailsBinding activityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_cuntry_details);

        // bind RecyclerView
        RecyclerView recyclerView = activityBinding.viewCountries;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //divider
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));



        viewModel = ViewModelProviders.of(this).get(CountryDetailsViewModel.class);
        activityBinding.setMyViewModel(viewModel);
        countryAdapter = new CountriesAdapter();
        recyclerView.setAdapter(countryAdapter);
        getAllCountries();


    }
    private void getAllCountries() {
        ArrayList<Country> data = getIntent().getParcelableArrayListExtra("borders");
        viewModel.getAllCountries(data).observe(this, countries -> {
            viewModel.response().set(Status.SUCCESS);
            countryAdapter.setList((ArrayList<Country>) countries);
        });

    }

}
