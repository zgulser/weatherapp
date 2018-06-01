package com.ebay.codingexercise.apps.weatherinfo.view.adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebay.codingexercise.apps.weatherinfo.R;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppRecentItemBinding;
import com.ebay.codingexercise.apps.weatherinfo.view.adapter.viewmodel.ItemViewModel;

import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.WeatherItemHolder> {

    private WeatherItemClickedListener weatherItemClickedListener;
    private final Context context;
    private final List<ItemViewModel> itemViewModelList;

    public ItemAdapter(Context context,
                       List<ItemViewModel> itemViewModelList,
                       WeatherItemClickedListener weatherItemClickedListener) {
        this.context = context;
        this.itemViewModelList = itemViewModelList;
        this.weatherItemClickedListener = weatherItemClickedListener;
    }

    public interface WeatherItemClickedListener{
        void onClicked(ItemViewModel itemViewModel);
    }

    @Override
    public WeatherItemHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        WeatherAppRecentItemBinding itemBinding = WeatherAppRecentItemBinding.inflate(layoutInflater, viewGroup, false);
        return new WeatherItemHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(WeatherItemHolder weatherItemHolder, int position) {
        weatherItemHolder.bind(itemViewModelList.get(weatherItemHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return itemViewModelList.size();
    }

    public class WeatherItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemViewModel itemViewModel;
        private WeatherAppRecentItemBinding bindingView;

        public WeatherItemHolder(WeatherAppRecentItemBinding bindingView) {
            super(bindingView.getRoot());
            this.bindingView = bindingView;
            this.bindingView.getRoot().setOnClickListener(this);
        }

        private void bind(ItemViewModel itemViewModel){
            this.itemViewModel = itemViewModel;
            bindingView.name.setText(itemViewModel.getName());
            String query = bindingView.getRoot().getContext().getResources().getString(R.string.weather_app_search_query);
            bindingView.query.setText(String.format(query, itemViewModel.getQuery().getQuery()));
            bindingView.date.setText(itemViewModel.getDate());
        }

        @Override
        public void onClick(View v) {
            if (weatherItemClickedListener != null){
                weatherItemClickedListener.onClicked(itemViewModel);
            }
        }
    }
}


