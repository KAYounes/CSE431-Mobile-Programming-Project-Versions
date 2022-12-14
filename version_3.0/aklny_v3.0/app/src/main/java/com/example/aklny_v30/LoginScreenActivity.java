package com.example.aklny_v30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginScreenActivity extends AppCompatActivity {

    private final static int REQUEST_CODE_SIGN_IN = 100;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        firebaseAuth = FirebaseAuth.getInstance();


        SignInButton btn_google_sing_in = findViewById(R.id.btn_google_sign_in);
        btn_google_sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Exception taskException = task.getException();
            if(task.isSuccessful())
            {
                try
                {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                    firebaseAuthWithGoogle(account.getIdToken());
                }catch (ApiException apiException)
                {
                    Toast.makeText(this, "API exception", Toast.LENGTH_SHORT).show();
                }
            }


        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, (task) -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(this, "signInWithCredential SUCCESSFUL", Toast.LENGTH_SHORT).show();
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Intent intent = new Intent(LoginScreenActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(this, "signInWithCredential FAILED", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}