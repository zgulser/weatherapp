package com.ebay.codingexercise.apps.weatherinfo.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebay.codingexercise.apps.weatherinfo.R;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.DefaultDiskCacheProvider;
import com.ebay.codingexercise.apps.weatherinfo.core.dto.Query;
import com.ebay.codingexercise.apps.weatherinfo.core.cache.SearchDiskCache;
import com.ebay.codingexercise.apps.weatherinfo.databinding.WeatherAppRecentsFragmentBinding;
import com.ebay.codingexercise.apps.weatherinfo.view.adapter.ItemAdapter;
import com.ebay.codingexercise.apps.weatherinfo.view.adapter.viewmodel.ItemViewModel;
import com.ebay.codingexercise.apps.weatherinfo.view.custom.error.ErrorItem;
import com.ebay.codingexercise.apps.weatherinfo.view.custom.error.StatusView;
import com.ebay.codingexercise.apps.weatherinfo.view.util.AdapterConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeki Gulser on 31/05/2018.
 */
public class RecentsFragment extends Fragment
                             implements ItemAdapter.WeatherItemClickedListener,
                                        SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = RecentsFragment.class.getSimpleName();
    private WeatherAppRecentsFragmentBinding bindingView;
    private ItemAdapter adapter;
    private RecentsFragmentListener recentsFragmentListener;
    private StatusView emptyView;
    private OnQueriesReceived onQueriesReceived = new OnQueriesReceived();

    interface RecentsFragmentListener {
        void onItemClicked(Query query);
    }

    public static RecentsFragment newInstance(Bundle bundle) {
        RecentsFragment fragment = new RecentsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.bindingView = DataBindingUtil.inflate(inflater, R.layout.weather_app_recents_fragment, container, false);
        this.bindingView.swipeRefreshLayout.setOnRefreshListener(this);
        return bindingView.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.adapter = new ItemAdapter(getContext(), new ArrayList<ItemViewModel>(), this);
        this.bindingView.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.onRefresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(onQueriesReceived,
                new IntentFilter(SearchDiskCache.EVENT_QUERY_OBJECT_READ));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(onQueriesReceived);
    }

    @Override
    public void onClicked(ItemViewModel itemViewModel) {
        recentsFragmentListener.onItemClicked(itemViewModel.getQuery());
    }

    @Override
    public void onRefresh() {
        this.bindingView.swipeRefreshLayout.setEnabled(false);
        SearchDiskCache.getInstance().readObjectList(getContext().getApplicationContext(), null);
    }

    private void updateView(List<Query> queryList){
        if (queryList.isEmpty()){
            addStatusView(ErrorItem.ErrorType.EMPTY);
        } else {
            removeStatusView();
            bindingView.swipeRefreshLayout.setEnabled(true);
            bindingView.swipeRefreshLayout.setRefreshing(false);
            adapter = new ItemAdapter(getContext(),
                    AdapterConverter.convertQueriesToViewModels(queryList),this);
            RecentsFragment.this.bindingView.recyclerView.setAdapter(adapter);
        }
    }

    private void addStatusView(ErrorItem.ErrorType errorType){
        if (emptyView == null){
            this.emptyView = new StatusView(getContext());
            this.emptyView.getRetryButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh();
                }
            });
        }
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(bindingView.swipeRefreshLayout.getLayoutParams());
        params.setMargins(0, 0, 0, 0);
        emptyView.setLayoutParams(params);
        emptyView.addView(emptyView);
        emptyView.show(errorType);
    }

    private void removeStatusView() {
        if (emptyView != null) {
            this.bindingView.parentView.removeView(emptyView);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecentsFragmentListener  ){
            recentsFragmentListener = (RecentsFragmentListener ) context;
        }
    }

    public class OnQueriesReceived extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
           List<Query> queryList = getQueryListFromIntent(intent);
            if(queryList != null) {
                if (!isDetached() && !isRemoving()){
                    updateView(queryList);
                }
            } else {
                Log.e(TAG, "OnQueriesReceived.onReceive: query list returned null");
                throw new NullPointerException("Invalid query data received");
            }
        }
    }

    @Nullable
    private List<Query> getQueryListFromIntent(@NonNull Intent intent) {
        if (intent.getExtras() != null){
            return intent.getExtras().getParcelableArrayList(DefaultDiskCacheProvider.BUNDLE_QUERY_LIST_KEY);
        }
        return null;
    }
}
