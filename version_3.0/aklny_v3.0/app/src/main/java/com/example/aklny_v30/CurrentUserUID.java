package com.example.aklny_v30;

import com.example.aklny_v30.models.user_model.UserModel;

abstract public class CurrentUserUID {
    private static String CURRENT_USER_UID;

    static public void setCurrentUserUid(String uid){
        CURRENT_USER_UID = uid;
    }

    static public String getCurrentUserUid(){
        return CURRENT_USER_UID;
    }

    static public void removeCurrentUser(){
        CURRENT_USER_UID = null;
    }
}
