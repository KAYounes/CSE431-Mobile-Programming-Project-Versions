package com.example.aklny_v30.API;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityAipBinding;

public class AipActivity extends AppCompatActivity {
    ActivityAipBinding binder;
    MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityAipBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.init();



        viewModel.getTimeZoneResponse().observe(this, new Observer<ResponsesModel>() {
            @Override
            public void onChanged(ResponsesModel responsesModel) {
                Toast.makeText(AipActivity.this, "Time is " + responsesModel.getDateTime(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getTimeZone();
    }
}