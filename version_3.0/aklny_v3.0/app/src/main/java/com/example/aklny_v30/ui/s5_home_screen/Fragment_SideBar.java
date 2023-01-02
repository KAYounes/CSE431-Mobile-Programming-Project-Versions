package com.example.aklny_v30.ui.s5_home_screen;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aklny_v30.databinding.FragmentSideBarBinding;
import com.example.aklny_v30.ui.Activity_ProfileScreen;
import com.example.aklny_v30.ui.ACT_ViewUsers;
import com.example.aklny_v30.ui.admin.ActivityAddMenu;
import com.example.aklny_v30.ui.admin.ActivityAddRestaurant;
import com.example.aklny_v30.ui.s2_landing_screen.ACT_LandingScreen;
import com.example.aklny_v30.ui.s9_previous_orders.ACT_PreviousOrdersScreen;
import com.google.firebase.auth.FirebaseAuth;

public class Fragment_SideBar extends Fragment {
    FragmentSideBarBinding binder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binder = FragmentSideBarBinding.inflate(inflater, container, false);
        
        binder.closeSideBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
            }
        });

        binder.btnCloseSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
            }
        });
        
        binder.menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(requireActivity().getClass() != Activity_HomeScreen.class){
                    startActivity(new Intent(requireActivity(), Activity_HomeScreen.class));
                    getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
                }
            }
        });

        binder.menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (requireActivity().getClass() != Activity_ProfileScreen.class) {
                    startActivity(new Intent(requireActivity(), Activity_ProfileScreen.class));
                    getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
                }
            }
        });

        binder.menuOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (requireActivity().getClass() != ACT_PreviousOrdersScreen.class) {
                    startActivity(new Intent(requireActivity(), ACT_PreviousOrdersScreen.class));
                    getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
                }

            }
        });

        binder.menuViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(requireActivity(), ACT_ViewUsers.class));
                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
            }
        });

        binder.menuAddRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(requireActivity(), ActivityAddRestaurant.class));
                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
            }
        });

        binder.menuAddDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(requireActivity(), ActivityAddMenu.class));
                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
            }
        });

        binder.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getParentFragmentManager().beginTransaction().remove(Fragment_SideBar.this).commit();
                FirebaseAuth.getInstance().signOut();
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                startActivity(new Intent(requireActivity(), ACT_LandingScreen.class));
                requireActivity().sendBroadcast(broadcastIntent);

                requireActivity().finish();
            }
        });

        return binder.getRoot();
    }
}