package com.example.aklny_v30.ui.s5_home_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aklny_v30.databinding.FragmentSideBarBinding;
import com.example.aklny_v30.ui.admin.ActivityAddMenu;
import com.example.aklny_v30.ui.admin.ActivityAddRestaurant;
import com.example.aklny_v30.ui.s2_landing_screen.LandingScreenActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_SideBar extends Fragment {
    FragmentSideBarBinding binder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentSideBarBinding.inflate(inflater, container, false);

        binder.btnCloseSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
            }
        });
        
        binder.menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireActivity(), "Click", Toast.LENGTH_SHORT).show();
            }
        });

        binder.menuOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireActivity(), "Orders", Toast.LENGTH_SHORT).show();
            }
        });

        binder.menuViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireActivity(), "View Users", Toast.LENGTH_SHORT).show();
            }
        });

        binder.menuAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireActivity(), "Add Restaurant", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), ActivityAddRestaurant.class));
                requireActivity().finish();
            }
        });

        binder.menuAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireActivity(), "Add Dish", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), ActivityAddMenu.class));
                requireActivity().finish();
            }
        });

        binder.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(requireActivity(), "Add Dish", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), LandingScreenActivity.class));
                FirebaseAuth.getInstance().signOut();
                requireActivity().finish();
            }
        });

        return binder.getRoot();
    }
}