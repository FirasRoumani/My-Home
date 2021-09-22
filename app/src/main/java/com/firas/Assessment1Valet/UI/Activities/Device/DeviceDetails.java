package com.firas.Assessment1Valet.UI.Activities.Device;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.firas.Assessment1Valet.Application.MyHomeApp;
import com.firas.Assessment1Valet.ModelLayer.Models.Device;
import com.firas.Assessment1Valet.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class DeviceDetails extends AppCompatActivity implements DevicePresenter.View {
    @Inject
    DevicePresenter mPresenter;
    Device device;

    private TextView device_type;
    private ImageView device_image;
    private TextView device_price;
    private RatingBar device_rating;

    public static final String BUNDLE_DEVICE = "BUNDLE_DEVICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail);
        device_type = (TextView) findViewById(R.id.tv_device_type);
        device_image = (ImageView) findViewById(R.id.device_image);
        device_price = (TextView) findViewById(R.id.tv_device_price);
        device_rating = (RatingBar) findViewById(R.id.rating);

        ((MyHomeApp) getApplication()).getAppComponent().inject(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            device = (Device) extras.getSerializable(BUNDLE_DEVICE);
        }

        mPresenter.setView(this);

        if (device != null)
            mPresenter.getDevice(device);

    }

    @Override
    public void showDevice(Device device) {
        if (device != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("  " + device.getTitle());
            getSupportActionBar().setSubtitle(device.getStatus() == 1 ? "  Available" : "  Not Available");
            device_type.setText(device.getType());
            Picasso.with(getApplicationContext()).load(device.getImageUrl().isEmpty() ? null : device.getImageUrl())
                    .fit().centerCrop()
                    .placeholder(R.drawable.smart_home_icon)
                    .error(R.drawable.ic_action_error)
                    .into(device_image);
            device_price.setText(device.getPrice() + " " + device.getCurrency());
            device_rating.setRating((float) device.getRating());

            if (device.isFavorite())
                getSupportActionBar().setIcon(R.drawable.ic_action_favorites);
            else
                getSupportActionBar().setIcon(R.drawable.ic_action_not_favorites);


        }
    }
}
