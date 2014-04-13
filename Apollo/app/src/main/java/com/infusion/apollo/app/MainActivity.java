package com.infusion.apollo.app;

import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {
    @InjectView(R.id.imageView)
    private ImageView mImageView;

    @InjectView(R.id.descriptionView)
    private TextView mDescriptionView;

    @Inject
    private Vibrator mVibrator;

    @InjectResource(R.string.take_nap)
    public String mTakeNap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImageView.setBackgroundResource(R.drawable.lolcats);
        mImageView.setOnClickListener(mImageViewClickListener);
    }

    private final View.OnClickListener mImageViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mDescriptionView.setText(mTakeNap);
            mVibrator.vibrate(1000);

            // sample toast message
            Toast.makeText(view.getContext(), mTakeNap, Toast.LENGTH_SHORT).show();
        }
    };
}
