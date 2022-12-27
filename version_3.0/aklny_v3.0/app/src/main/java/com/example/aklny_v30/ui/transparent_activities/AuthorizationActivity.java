package com.example.aklny_v30.ui.transparent_activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.aklny_v30.R;
import com.example.aklny_v30.repos.UsersRepo;
import com.example.aklny_v30.repos.firebase.FbUserRepo;
import com.example.aklny_v30.models.user_model.UserModel;
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

    private boolean showOneTapUI = true;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private BeginSignInRequest signUpRequest;
    private FirebaseAuth mAuth;
    private FbUserRepo fbUserRepo;
    private UsersRepo usersRepo;
    static public final int AUTHENTICATION_REQUEST_CODE = 1000;  // Can be any integer unique to the Activity.

    int action;
    static public final int NO_ACTION = 0;
    static public final int SIGN_UP_WITH_GOOGLE = 10;
    static public final int SIGN_UP_WITH_PASSWORD = 20;
    static public final int SIGN_IN_WITH_GOOGLE = 30;
    static public final int SIGN_IN_WITH_PASSWORD = 40;

    static public String RESULT_KEY = "Authentication_Result";
    static public final String AUTHENTICATION_SUCCESS = "AUTHENTICATION_SUCCESS";
    static public final String AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
    static public final String USER_EXISTS = "USER_EXISTS";
    static public final String USER_DOES_NOT_EXIST = "USER_DOES_NOT_EXIST";

    private String firstName, lastName, phoneNumber, email, password;
    private String TAG = "PRINT";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        mAuth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        fbUserRepo = new FbUserRepo();
        usersRepo = new UsersRepo(getApplication());


        Intent authorizationIntent = getIntent();
        action = authorizationIntent.getIntExtra("ACTION", NO_ACTION);

        Log.d(TAG, "onCreate: " + action);

        switch (action){
            case SIGN_UP_WITH_GOOGLE:
                firstName = authorizationIntent.getStringExtra("FIRSTNAME");
                lastName = authorizationIntent.getStringExtra("LASTNAME");
                phoneNumber = authorizationIntent.getStringExtra("PHONENUMBER");
                Log.d(TAG, "SIGN_UP_WITH_GOOGLE");
                signUpWithGoogle();
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
            case SIGN_IN_WITH_GOOGLE:
                Log.d(TAG, "SIGN_IN_WITH_GOOGLE");
                signInWithGoogle();
                break;
            case SIGN_IN_WITH_PASSWORD:
                email = authorizationIntent.getStringExtra("EMAIL");
                password = authorizationIntent.getStringExtra("PASSWORD");
                Log.d(TAG, "SIGN_IN_WITH_PASSWORD");
                signInWithPassword();
                break;
            case NO_ACTION:
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Toast.makeText(this, "Please wait while we authenticate your account", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode)
        {
            case AUTHENTICATION_REQUEST_CODE:
                try
                {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken !=  null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
//                        Log.d("PRINT", "Got ID token.");
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
                    returnResult(RESULT_KEY, message);
                    break;
                }
        }
    }


    private void signInWithCredentials(AuthCredential firebaseCredential)
    {
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
                            checkUserIsInFirebaseDatabase();

                        } else
                        {
                            // If sign in fails
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            returnResult(RESULT_KEY, "signInWithCredential:failure");
                        }
                    }
                });
    }

    private void beginRequest(BeginSignInRequest request)
    {
        oneTapClient.beginSignIn(request)
                .addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
                    @Override
                    public void onSuccess(BeginSignInResult beginSignInResult) {
                        try {
                            startIntentSenderForResult(
                                    beginSignInResult.getPendingIntent().getIntentSender(), AUTHENTICATION_REQUEST_CODE,
                                    null, 0, 0, 0);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "Couldn't start One Tap UI: " + e.getLocalizedMessage());
                            returnResult(RESULT_KEY, "startIntentSenderForResult Exception > " + e.getLocalizedMessage());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "FAILED> " + e.getLocalizedMessage());
                        returnResult(RESULT_KEY, "beginSignIn FAILED > " + e.getLocalizedMessage());
                    }
                });
    }


/**------------------------------------Authentication Methods------------------------------------**/
    private void signInWithGoogle()
    {
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        beginRequest(signInRequest);
    }

    private void signUpWithGoogle()
    {
        signUpRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.default_web_client_id))
                        // Show all accounts on the device.
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build();

        beginRequest(signUpRequest);
    }

    private void signInWithPassword()
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            returnResult(RESULT_KEY, AUTHENTICATION_SUCCESS);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            returnResult(RESULT_KEY, AUTHENTICATION_FAILED);
                        }
                    }
                });
    }

    private void signUpWithPassword()
    {

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

                                            usersRepo.getUser(mAuth.getUid()).observe(AuthorizationActivity.this, new Observer<UserModel>() {
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
                                                    returnResult(RESULT_KEY, AUTHENTICATION_SUCCESS);
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(AuthorizationActivity.this, new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "addUserToFirebaseDatabase: failure");
                                            mAuth.getCurrentUser().delete();
                                        }
                                    });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            returnResult(RESULT_KEY, USER_EXISTS);
                        }
                    }
                });
    }


/**------------------------------------- Model Controllers -------------------------------------**/
    private void checkUserIsInFirebaseDatabase()
    {
        fbUserRepo.getUser(mAuth.getUid())
                .addOnCompleteListener(AuthorizationActivity.this, new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful())
                {
                    if (task.getResult().getValue() != null)
                    {
                        Log.d(TAG, "checkUserIsInFirebaseDatabase > User Found > " + task.getResult().getValue());
                        if(action == SIGN_UP_WITH_GOOGLE)
                        {
                            mAuth.getCurrentUser().delete();
                            returnResult(RESULT_KEY, AUTHENTICATION_FAILED);
                        }
                        else if (action == SIGN_IN_WITH_GOOGLE)
                        {
                            returnResult(RESULT_KEY, AUTHENTICATION_SUCCESS);
                        }
//                        returnResult(RESULT_KEY, USER_EXISTS);
                    }
                    else
                    {
                        Log.d(TAG, "checkUserIsInFirebaseDatabase > User Not Found");

                        if(action == SIGN_UP_WITH_GOOGLE)
                        {
                            email = mAuth.getCurrentUser().getEmail();
                            UserModel newUser = new UserModel(mAuth.getUid(), firstName, lastName, phoneNumber, email);
                            addUserToDatabases(newUser);
                        }
                        else if (action == SIGN_IN_WITH_GOOGLE)
                        {
                            mAuth.getCurrentUser().delete();
                            returnResult(RESULT_KEY, AUTHENTICATION_FAILED);
                        }
                    }
                }
                else
                {
                    Log.d(TAG, "checkUserIsInFirebaseDatabase > FAILED");
                    returnResult(RESULT_KEY, AUTHENTICATION_FAILED);
                }
            }
        });
    }

    private void addUserToDatabases(UserModel newUser)
    {
        addUserToFirebaseDatabase(newUser)
                .addOnCompleteListener(AuthorizationActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "addUserToFirebaseDatabase: complete");
                        addUserToRoomDatabase(newUser);

                        usersRepo.getUser(mAuth.getUid())
                                .observe(AuthorizationActivity.this, new Observer<UserModel>() {
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
                                returnResult(RESULT_KEY, AUTHENTICATION_SUCCESS);
                            }
                        });
                    }
                })
                .addOnFailureListener(AuthorizationActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "addUserToFirebaseDatabase: failure");
                        mAuth.getCurrentUser().delete();
                    }
                });
    }

    private Task<Void> addUserToFirebaseDatabase(UserModel newUser)
    {
        return fbUserRepo.addUser(newUser);
    }

    private void addUserToRoomDatabase(UserModel newUser)
    {
        usersRepo.addUser(newUser);
    }


/**---------------------------- Return Result and End Authentication ----------------------------**/
    private void returnResult(String key, String message)
    {
        Intent intent = new Intent();
        intent.putExtra(key, message);
        setResult(1000, intent);
        finish();
        this.overridePendingTransition(0, 0);
    }
}