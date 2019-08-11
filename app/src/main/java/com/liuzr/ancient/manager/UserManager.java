/*
 * Created by wingjay on 11/16/16 3:32 PM
 * Copyright (c) 2016.  All rights reserved.
 *
 * Last modified 11/10/16 11:05 AM
 *
 * Reach me: https://github.com/wingjay
 * Email: yinjiesh@126.com
 */

package com.liuzr.ancient.manager;

import android.content.Context;

import com.liuzr.ancient.prefs.UserPrefs;


/**
 * User Management.
 */

public class UserManager {

//    Lazy<UserPrefs> userPrefsLazy;


    UserPrefs userPrefs;
    private static UserManager sUserManager;

    public UserManager() {
    }


    public static UserManager getInstance(Context context) {
        if (sUserManager == null) {
            sUserManager = new UserManager();
        }
        return sUserManager;
    }

}
