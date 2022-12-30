package com.example.aklny_v30.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aklny_v30.Constants;
import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.ActivityProfileScreenBinding;
import com.example.aklny_v30.models.UserModel;
import com.example.aklny_v30.viewModels.VModel_ProfileScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Activity_ProfileScreen extends AppCompatActivity {
    ActivityProfileScreenBinding binder;
    VModel_ProfileScreen viewModel;
    UserModel user;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = ActivityProfileScreenBinding.inflate(getLayoutInflater());
        setContentView(binder.getRoot());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGOUT");
        registerReceiver(receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                finish();
            }
        }, intentFilter);

        viewModel = new ViewModelProvider(this).get(VModel_ProfileScreen.class);
        binder.btnSaveChanges.setEnabled(false);

        viewModel.getUser().observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel fetchedUser) {

                Picasso.get().load(fetchedUser.getPhotoURL())
                        .placeholder(R.drawable.icon_profile_placeholder)

                        .into(binder.profileImage);
                user = fetchedUser;
                binder.textInputEditPhotoURL.setText(fetchedUser.getPhotoURL());
                binder.textInputEditFirstName.setText(fetchedUser.getFirstName());
                binder.textInputEditLastName.setText(fetchedUser.getLastName());
                binder.textInputEditPhoneNumber.setText(fetchedUser.getPhoneNumber());
                binder.btnSaveChanges.setEnabled(false);

                settings = getSharedPreferences(Constants.SHARED_PREFERENCE_KEY_DEFAULT_GATE + user.getAuth_uid(), Context.MODE_PRIVATE);
                editor = settings.edit();
                int default_gate = settings.getInt(Constants.SHAREDPREFERENCE_PUT_KEY_GATE + user.getAuth_uid(), 0);
                if( default_gate == binder.gate3.getId()){
                    binder.gate3.setChecked(true);
                }
                if( default_gate == binder.gate4.getId()){
                    binder.gate4.setChecked(true);
                }
            }
        });

        binder.gateRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int gate = binder.gateRadioGroup.getCheckedRadioButtonId();

                editor.putInt(Constants.SHAREDPREFERENCE_PUT_KEY_GATE + FirebaseAuth.getInstance().getUid(), i);
                editor.commit();
                Toast.makeText(Activity_ProfileScreen.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        binder.textInputEditFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(binder.textInputEditFirstName.hasFocus()){binder.btnSaveChanges.setEnabled(true);}
            }
        });
        binder.textInputEditLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(binder.textInputEditLastName.hasFocus()){binder.btnSaveChanges.setEnabled(true);}
            }
        });
        binder.textInputEditPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(binder.textInputEditPhoneNumber.hasFocus()){binder.btnSaveChanges.setEnabled(true);}
            }
        });
        binder.textInputEditPhotoURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if(binder.textInputEditPhotoURL.hasFocus()){binder.btnSaveChanges.setEnabled(true);}
            }
        });

        binder.btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForEmptyFields()){
                    Toast.makeText(Activity_ProfileScreen.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                user.setPhotoURL(binder.textInputEditPhotoURL.getText().toString());
                user.setFirstName(binder.textInputEditFirstName.getText().toString());
                user.setLastName(binder.textInputEditLastName.getText().toString());
                user.setPhoneNumber(binder.textInputEditPhoneNumber.getText().toString());
                viewModel.updateUser(user);
                binder.btnSaveChanges.setEnabled(false);
                Toast.makeText(Activity_ProfileScreen.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkForEmptyFields() {
        return binder.textInputEditPhotoURL.getText().toString().isEmpty()
                || binder.textInputEditFirstName.getText().toString().isEmpty()
                || binder.textInputEditLastName.getText().toString().isEmpty()
                || binder.textInputEditPhoneNumber.getText().toString().isEmpty();
    }
}