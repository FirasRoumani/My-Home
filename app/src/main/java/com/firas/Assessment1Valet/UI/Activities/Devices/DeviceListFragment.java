package com.firas.Assessment1Valet.UI.Activities.Devices;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firas.Assessment1Valet.Application.MyHomeApp;
import com.firas.Assessment1Valet.Helpers.AppPreferences;
import com.firas.Assessment1Valet.ModelLayer.Models.Device;
import com.firas.Assessment1Valet.ModelLayer.Network.NetworkErrorDescriptor;
import com.firas.Assessment1Valet.R;
import com.firas.Assessment1Valet.UI.Activities.Device.DeviceDetails;
import com.firas.Assessment1Valet.UI.Activities.MainActivity;
import com.firas.Assessment1Valet.UI.Adapters.DeviceAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DeviceListFragment extends Fragment implements DeviceListPresenter.View {
    @Inject
    DeviceListPresenter mPresenter;
    @Inject
    SearchManager mSearchManager;
    @Inject
    AppPreferences mAppPreferences;

    public static final String BUNDLE_KEY_FAVORITE = "BUNDLE_KEY_FAVORITE";
    private RecyclerView deviceRecyclerView;
    private ProgressBar mProgressBar;
    private DeviceAdapter deviceListAdapter;

    boolean isSortDesc = true;
    boolean isFavorite = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((MyHomeApp) requireActivity().getApplication()).getAppComponent().inject(this);
        isFavorite = getArguments() != null && getArguments().getBoolean(BUNDLE_KEY_FAVORITE);

        mPresenter.setView(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_device_list, container, false);

        mProgressBar = rootView.findViewById(R.id.progress_bar);
        deviceRecyclerView = rootView.findViewById(R.id.recycler_view_devices);


        setupRecyclerView(new ArrayList<>());
        mPresenter.getDevices();

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();

        requireActivity().getMenuInflater().inflate(R.menu.menu_device_list, menu);
        ((MainActivity) getActivity()).searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        ((MainActivity) getActivity()).searchView.setSearchableInfo(mSearchManager
                .getSearchableInfo(getActivity().getComponentName()));
        ((MainActivity) getActivity()).searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        ((MainActivity) getActivity()).searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAppPreferences.hideSoftKeyboard(requireActivity());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                deviceListAdapter.getFilter().filter(query);
                return true;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
                return true;

            case R.id.action_sort_by_name:
                isSortDesc = !isSortDesc;
                deviceListAdapter.sortByName(isSortDesc);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(List<Device> devices) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

        deviceListAdapter = new DeviceAdapter(getContext(), R.layout.device_list_content);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireActivity().getApplicationContext());
        deviceRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        deviceRecyclerView.setItemAnimator(itemAnimator);
        deviceRecyclerView.setLayoutManager(manager);
        deviceRecyclerView.setAdapter(deviceListAdapter);
        deviceRecyclerView.setClickable(false);
        deviceListAdapter.setDeviceList(devices, isFavorite);
        deviceListAdapter.notifyDataSetChanged();
        deviceListAdapter.setOnItemClickListener(pos -> {
            Device selected_device = deviceListAdapter.getDeviceList().get(pos);
            Intent intent = new Intent(getActivity(), DeviceDetails.class);
            intent.putExtra(DeviceDetails.BUNDLE_DEVICE, (Serializable) selected_device);
            startActivity(intent);

        });
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Exception exception) {
        String message = NetworkErrorDescriptor.getDescription(getActivity(), exception);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDevices(List<Device> devices) {
        if (devices != null) {
            deviceListAdapter.setDeviceList(devices, isFavorite);
            deviceListAdapter.notifyDataSetChanged();
        }
    }


}
