package com.example.aklny_v30.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.aklny_v30.CurrentUserUID;
import com.example.aklny_v30.models.FirebaseUsersRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import io.reactivex.rxjava3.core.Single;

public class LoginScreenViewModel extends AndroidViewModel {
    public static enum userAccountStatus {USER_DOES_NOT_EXISTS, USER_EXISTS};
    private FirebaseUsersRepository firebaseRepo = new FirebaseUsersRepository();
    private userAccountStatus userExistsFlag;

    public LoginScreenViewModel(Application application) {
        super(application);
    }

    //    private MutableLiveData<userAccountStatus> _flagUserExists = new MutableLiveData<>();
//    public LiveData<userAccountStatus> flagUserExists(){
//        return _flagUserExists;
//    }

    public Single<userAccountStatus> checkUserExists(String uid){
        return Single.create(emitter -> {
            firebaseRepo.usersRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task)
                {
                    if (task.isSuccessful())
                    {
                        Boolean result = task.getResult().exists();

                        if(result)
                        {
                            Log.d("PRINT", "userExits > true");
    //                        _flagUserExists.setValue(userAccountStatus.USER_EXISTS);
                            userExistsFlag = userAccountStatus.USER_EXISTS;
                        }
                        else
                        {
                            Log.d("PRINT", "userExits > false");
    //                        _flagUserExists.setValue(userAccountStatus.USER_DOES_NOT_EXISTS);
                            userExistsFlag = userAccountStatus.USER_DOES_NOT_EXISTS;
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

    public void assignCurrentUser(String uid){
        CurrentUserUID.setCurrentUserUid(uid);
    }
}
