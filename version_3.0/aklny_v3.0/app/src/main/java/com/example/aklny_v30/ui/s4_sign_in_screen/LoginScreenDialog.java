package com.example.aklny_v30.ui.s4_sign_in_screen;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.example.aklny_v30.R;
import com.example.aklny_v30.databinding.DialogFailedLoginBinding;

public class LoginScreenDialog {
    private final Dialog dialog;
    private final DialogFailedLoginBinding binder;

    public LoginScreenDialog(Activity activity) {
        this.dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(activity.getDrawable(R.drawable.dialog_round_no_outline));
        this.binder = DialogFailedLoginBinding.inflate(LayoutInflater.from(activity));
        binder.dialogButtonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void startLoadingDialog(){
        dialog.setContentView(R.layout.dialog_logging_in);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void successfulLoginDialog(){
        dialog.setContentView(R.layout.dialog_successful_login);
        dialog.setCancelable(true);
    }

    public void failedLoginDialog(){
        dialog.setContentView(binder.getRoot());
        dialog.setCancelable(true);
        dialog.show();
    }


}
