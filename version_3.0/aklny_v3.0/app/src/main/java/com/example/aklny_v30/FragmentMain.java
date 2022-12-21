package com.example.aklny_v30;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aklny_v30.databinding.FragmentMainBinding;

public class FragmentMain extends Fragment {
    FragmentMainBinding binder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentMainBinding.inflate(inflater, container,false);

        return binder.getRoot();
    }
}