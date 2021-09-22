package com.firas.Assessment1Valet.UI.Adapters;

import static com.firas.Assessment1Valet.Helpers.Constants.ACTIVE;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firas.Assessment1Valet.Listeners.ItemClickListener;
import com.firas.Assessment1Valet.ModelLayer.Models.Device;
import com.firas.Assessment1Valet.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> implements Filterable {


    private List<Device> deviceList;
    private List<Device> deviceListFiltered;
    private final int displayLayout;
    private final Context context;
    private ItemClickListener mItemClickListener;

    public DeviceAdapter(Context context, int displayLayout) {
        this.deviceList = new ArrayList<>();
        this.deviceListFiltered = new ArrayList<>();
        this.displayLayout = displayLayout;
        this.context = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(displayLayout, parent, false),
                mItemClickListener);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final Device deviceObject = deviceListFiltered.get(position);

            if (deviceObject != null) {
                holder.device_name.setText(deviceObject.getTitle());
                Picasso.with(context).load(deviceObject.getImageUrl().isEmpty() ? null : deviceObject.getImageUrl())
                        .fit().centerCrop()
                        .placeholder(R.drawable.smart_home_icon)
                        .error(R.drawable.ic_action_error)
                        .into(holder.device_image);
                Drawable unwrappedDrawable = holder.device_status.getBackground();
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, deviceObject.getStatus() == ACTIVE ? context.getResources().getColor(R.color.colorGreen) : context.getResources().getColor(R.color.colorRed));

//                holder.device_status.setBackgroundColor(context.getResources().getColor(R.color.black));
                holder.device_image.setTag(deviceObject);
            }
        } catch (Exception ex) {
            Log.e("DeviceAdapter", "onBindViewHolder:  ", ex);
        }


    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView device_name;
        private final ImageView device_image;
        private final ImageView device_status;


        private MyViewHolder(@NonNull final View view, ItemClickListener listener) {
            super(view);
            device_name = (TextView) view.findViewById(R.id.device_name);
            device_image = (ImageView) view.findViewById(R.id.icon);
            device_status = (ImageView) view.findViewById(R.id.device_status);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return deviceListFiltered.size();
    }

    public List<Device> getDeviceList() {
        return deviceListFiltered;
    }

    public void setDeviceList(List<Device> deviceList, boolean favoriteOnly) {

        if (favoriteOnly) {
            List<Device> deviceFavoriteList = new ArrayList<Device>();
            for (Device current : deviceList) {
                if (current.isFavorite())
                    deviceFavoriteList.add(current);
            }
            this.deviceList = deviceFavoriteList;
            this.deviceListFiltered = deviceFavoriteList;
        } else {
            this.deviceList = deviceList;
            this.deviceListFiltered = deviceList;
        }

    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    deviceListFiltered = deviceList;
                } else {
                    List<Device> filteredList = new ArrayList<>();
                    for (Device row : deviceList) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    deviceListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = deviceListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                try {
                    deviceListFiltered = (ArrayList<Device>) filterResults.values;
                    notifyDataSetChanged();
                } catch (Exception ex) {
                    Log.e("Exception", Objects.requireNonNull(ex.getMessage()));
                }

            }
        };
    }


    public void sortByName(boolean desc) {
        if (desc) {
            Collections.reverse(deviceListFiltered);
        } else {
            Collections.sort(deviceListFiltered, (o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
        }
        notifyDataSetChanged();

    }


}
