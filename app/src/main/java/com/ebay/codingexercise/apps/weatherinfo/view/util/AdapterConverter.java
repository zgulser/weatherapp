package com.ebay.codingexercise.apps.weatherinfo.view.util;

import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.view.adapter.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class AdapterConverter {

    public static List<ItemViewModel> convertQueriesToViewModels(List<Query> queryList){
        List<ItemViewModel> itemViewModelList = new ArrayList<>();
        for (Query query : queryList){
            ItemViewModel viewModel = new ItemViewModel(query);
            itemViewModelList.add(viewModel);
        }
        return itemViewModelList;
    }
}
