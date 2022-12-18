package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.aklny_v30.models.FirebaseUsersRepository;
import com.example.aklny_v30.models.user_model.UserModel;
import com.example.aklny_v30.models.user_model.UsersRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;

import java.util.Observable;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class SignUpViewModel extends AndroidViewModel {
    private UsersRepository usersRepository;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUsersRepository firebaseUsersRepository = new FirebaseUsersRepository();
    private MutableLiveData<UserModel> user;
    public static enum userAccountStatus {USER_DOES_NOT_EXISTS, USER_EXISTS};
    private LoginScreenViewModel.userAccountStatus userExistsFlag;

    public LiveData<UserModel> getUser() {
        return usersRepository.getUser(firebaseAuth.getUid());
    }

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
    }

    public Task<AuthResult> authenticateUserWithEmailAndPassword(String email, String password){
        return firebaseAuth.createUserWithEmailAndPassword(email, password);
    }

    public boolean doesUserExists(){
        firebaseUsersRepository.getUser(firebaseAuth.getUid());
//        if(firebaseUsersRepository.getUser(firebaseAuth.getUid()) != null)
//        {
//            Log.d("PRINT", "current user> " + firebaseAuth.getUid());
//            return true;
//        }

        return true;
    }

    public void foo(){

    }

    public void addUserToDatabases(String firstName, String lastName, String phoneNumber) {
        String email = firebaseAuth.getCurrentUser().getEmail();
        UserModel newUser = new UserModel(firebaseAuth.getUid(), firstName, lastName, phoneNumber, email);
        addUserToRealTimeDatabase(newUser);
        addUserToRootDatabase(newUser);
    }

    public void addUserToDatabases(String firstName, String lastName, String phoneNumber, String email) {
        UserModel newUser = new UserModel(firebaseAuth.getUid(), firstName, lastName, phoneNumber, email);
        addUserToRealTimeDatabase(newUser);
        addUserToRootDatabase(newUser);
    }

    private void addUserToRealTimeDatabase(UserModel newUser) {
        firebaseUsersRepository.addUser(newUser);
    }

    private void addUserToRootDatabase(UserModel newUser) {
        usersRepository.addUser(newUser);
    }

    public Task<AuthResult> signInWithCredential(AuthCredential credential) {
        return firebaseAuth.signInWithCredential(credential);
    }

    public Single<LoginScreenViewModel.userAccountStatus> checkUserExists(){
        String uid = firebaseAuth.getUid();
        Log.d("PRINT", "userExits > " + uid);

        return Single.create(emitter -> {
            firebaseUsersRepository.getUser(uid).addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        Boolean result = task.getResult().exists();
                        Log.d("PRINT", "userExits > " + result);


                        if(result)
                        {
                            Log.d("PRINT", "userExits > true");
                            //                        _flagUserExists.setValue(userAccountStatus.USER_EXISTS);
                            userExistsFlag = LoginScreenViewModel.userAccountStatus.USER_EXISTS;
                        }
                        else
                        {
                            Log.d("PRINT", "userExits > false");
                            //                        _flagUserExists.setValue(userAccountStatus.USER_DOES_NOT_EXISTS);
                            userExistsFlag = LoginScreenViewModel.userAccountStatus.USER_DOES_NOT_EXISTS;
                        }

                        emitter.onSuccess(userExistsFlag);
                    }
                    else {
                        Log.d("PRINT", "userExists > task > FAILED to get data");
                        //                    _flagUserExists.setValue(userAccountStatus.FAILED_TO_CONNECT);
                        emitter.onError(new Exception("Failed to Connect"));
                    }
                }
            });
        });
    }
}
