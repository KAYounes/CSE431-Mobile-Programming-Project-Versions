package com.example.aklny_v30.ui_controllers.e_homeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aklny_v30.R;
import com.example.aklny_v30.ui_controllers.b_landingScreen.LandingScreenActivity;
import com.example.aklny_v30.ui_controllers.d_signInScreen.LoginScreenActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreenActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInOptions gsOptions;
    GoogleSignInClient gsClient;

    ShapeableImageView img_profile_img;
    TextView tv_name, tv_uuid;
    Button btn_sign_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

//        img_profile_img = findViewById(R.id.img_profile_img);
//        tv_name = findViewById(R.id.tv_name);
//        tv_uuid = findViewById(R.id.tv_uuid);
        btn_sign_out = findViewById(R.id.btn_sign_out);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

//        Picasso.get().load(currentUser.getPhotoUrl()).into(img_profile_img);
//        tv_name.setText(currentUser.getDisplayName());
//        tv_uuid.setText(currentUser.getUid());

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }

    private void signOut() {
        firebaseAuth.signOut();
        startActivity(new Intent(HomeScreenActivity.this, LandingScreenActivity.class));
        finish();
    }
}