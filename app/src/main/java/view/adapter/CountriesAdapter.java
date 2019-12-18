package view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.test.countries.R;
import com.test.countries.databinding.CountryItemBinding;

import handler.ItemClickListener;
import model.Country;
import view.activity.CuntryDetailsActivity;
import viewmodel.CountryDetailsViewModel;

import java.util.ArrayList;

public class CountriesAdapter   extends RecyclerView.Adapter<CountriesAdapter.CountryViewHolder> {
    private ItemClickListener   itemClickListener;

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ArrayList<Country> countries;

    public CountriesAdapter(ArrayList<Country> countries) {
        this.countries = countries;
    }

    public CountriesAdapter() {
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CountryItemBinding countryItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.country_item, viewGroup, false);
        return new CountryViewHolder(countryItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder viewHolder, int i) {
        Country current = countries.get(i);
        viewHolder.countryItemBinding.setCountry(current);
        viewHolder.itemView.setOnClickListener(v -> {
          if (itemClickListener!=null)
              itemClickListener.onItemClick(v,current);
        });
    }

    @Override
    public int getItemCount() {
        if (countries != null) {
            return countries.size();
        } else {
            return 0;
        }
    }

    public void setList(ArrayList<Country> employees) {
        this.countries = employees;
        notifyDataSetChanged();
    }

    class CountryViewHolder extends RecyclerView.ViewHolder {

        private CountryItemBinding countryItemBinding;

        public CountryViewHolder(@NonNull CountryItemBinding employeetListItemBinding) {
            super(employeetListItemBinding.getRoot());

            this.countryItemBinding = employeetListItemBinding;
        }
    }
}
