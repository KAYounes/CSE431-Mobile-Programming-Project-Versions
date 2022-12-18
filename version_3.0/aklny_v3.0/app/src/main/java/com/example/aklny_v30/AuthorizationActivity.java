package com.example.aklny_v30;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import com.example.aklny_v30.models.FirebaseUsersRepository;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.models.user_model.UsersRepository;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;

public class AuthorizationActivity extends AppCompatActivity {
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private BeginSignInRequest signUpRequest;
    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;
    private FirebaseAuth mAuth;
    private String TAG = "PRINT";
    private final int NO_ACTION = 0;
    private final int SIGN_UP_WITH_GOOGLE = 10;
    private final int SIGN_UP_WITH_PASSWORD = 20;
    private final int SIGN_IN_WITH_GOOGLE = 30;
    private final int SIGN_IN_WITH_PASSWORD = 40;
    private String firstName, lastName, phoneNumber, email, password;
    int action;
    private FirebaseUsersRepository firebaseUsersRepository;
    private UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        mAuth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        firebaseUsersRepository = new FirebaseUsersRepository();
        usersRepository = new UsersRepository(getApplication());


        Intent authorizationIntent = getIntent();
        action = authorizationIntent.getIntExtra("ACTION", NO_ACTION);

        Log.d(TAG, "onCreate: " + action);

        switch (action){
            case SIGN_IN_WITH_GOOGLE:
                break;
            case SIGN_IN_WITH_PASSWORD:
                email = authorizationIntent.getStringExtra("EMAIL");
                password = authorizationIntent.getStringExtra("PASSWORD");
                break;
            case SIGN_UP_WITH_GOOGLE:
                break;
            case SIGN_UP_WITH_PASSWORD:
                firstName = authorizationIntent.getStringExtra("FIRSTNAME");
                lastName = authorizationIntent.getStringExtra("LASTNAME");
                phoneNumber = authorizationIntent.getStringExtra("PHONENUMBER");
                email = authorizationIntent.getStringExtra("EMAIL");
                password = authorizationIntent.getStringExtra("PASSWORD");
                Log.d(TAG, "SIGN_UP_WITH_PASSWORD");
                signUpWithPassword();
                break;
            case NO_ACTION:
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case REQ_ONE_TAP:
                try
                {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        Log.d("PRINT", "Got ID token.");
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        signInWithCredentials(firebaseCredential);
                    }
                }
                catch (ApiException e)
                {
                    // ...
                    Log.d("PRINT", "Error.");
                    String message;
                    switch (e.getStatusCode()) {
                        case CommonStatusCodes.CANCELED:
                            Log.d(TAG, "One-tap dialog was closed.");
                            message = "One-tap dialog was closed.";
                            // Don't re-prompt the user.
                            showOneTapUI = false;
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.d(TAG, "One-tap encountered a network error.");
                            message = "One-tap encountered a network error.";
                            // Try again or just ignore.
                            break;
                        default:
                            Log.d(TAG, "Couldn't get credential from result." + e.getLocalizedMessage());
                            message = "Couldn't get credential from result." + e.getLocalizedMessage();
                            break;
                    }
                    returnResult("state", message);
                    break;
                }
        }
    }

    private void signInWithCredentials(AuthCredential firebaseCredential) {
        mAuth.signInWithCredential(firebaseCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            // Sign in success
                            Log.d(TAG, "signInWithCredential:success");
                            returnResult("state", "signInWithCredential:success");
                        } else
                        {
                            // If sign in fails
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            returnResult("state", "signInWithCredential:failure");
                        }
                    }
                });
    }

    private void returnResult(String key, String message){
        Intent intent = new Intent();
        intent.putExtra(key, message);
        setResult(1000, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuth.signOut();
    }

    private void signIn(){
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
//                .setAutoSelectEnabled(true) // Automatically sign in when exactly one credential is retrieved.
                .build();

        beginRequest(signInRequest);
    }

    private void signUpWithGoogle(){
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .setRequestVerifiedPhoneNumber(true)
                        .build())
                .build();

        beginRequest(signUpRequest);
    }

    private void signInWithPassword(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    private void signUpWithPassword(){

        Log.d(TAG, "signUpWithPassword > " + email + " " + password);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            UserModel newUser = new UserModel(mAuth.getUid(), firstName, lastName, phoneNumber, email);
                            addUserToFirebaseDatabase(newUser)
                                    .addOnCompleteListener(AuthorizationActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Log.d(TAG, "addUserToFirebaseDatabase: complete");
                                            addUserToRoomDatabase(newUser);

                                            usersRepository.getUser(mAuth.getUid()).observe(AuthorizationActivity.this, new Observer<UserModel>() {
                                                @Override
                                                public void onChanged(UserModel userModel) {
                                                    try
                                                    {
                                                        Log.d(TAG, "addUserToRoomDatabase: changed > uid > " + userModel.getAuth_uid());
                                                    }
                                                    catch (Exception e)
                                                    {
                                                        Log.d(TAG, "addUserToRoomDatabase: changed > uid > " + e.getLocalizedMessage());
                                                    }
                                                    returnResult("state", "Account Created From Email");
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(AuthorizationActivity.this, new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "addUserToFirebaseDatabase: failure");
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            returnResult("state", "Account Created From Email");
                        }
                    }
                });
    }

    private void beginRequest(BeginSignInRequest request){
        oneTapClient.beginSignIn(request)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try {
                            startIntentSenderForResult(
                                    beginSignInResult.getPendingIntent().getIntentSender(), REQ_ONE_TAP,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                            returnResult("state", "startIntentSenderForResult Exception > " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "FAILED> " + e.getLocalizedMessage());
                        returnResult("state", "beginSignIn FAILED > " + e.getLocalizedMessage());
                    }
                });
    }
    
    private void checkUserIsInFirebaseDatabase(){
        firebaseUsersRepository.getUser(mAuth.getUid()).addOnCompleteListener(AuthorizationActivity.this, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    Log.d(TAG, "checkUserIsInFirebaseDatabase > User Found");
                }
                else
                {
                    Log.d(TAG, "checkUserIsInFirebaseDatabase > User Not Found");
                }
            }
        });
    }

    private void checkUserIsInRoomDatabase(){
        usersRepository.userExists(mAuth.getUid());
    }

    private Task<Void> addUserToFirebaseDatabase(UserModel newUser){
        return firebaseUsersRepository.addUser(newUser);
    }

    private void addUserToRoomDatabase(UserModel newUser){
        usersRepository.addUser(newUser);
    }
}