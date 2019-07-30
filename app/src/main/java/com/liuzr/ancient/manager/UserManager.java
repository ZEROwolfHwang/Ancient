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
import androidx.annotation.NonNull;

import com.liuzr.ancient.db.model.Diary;
import com.liuzr.ancient.db.model.PushData;
import com.liuzr.ancient.prefs.UserPrefs;
import com.liuzr.ancient.ui.SignupActivity;
import com.raizlabs.android.dbflow.sql.language.SQLite;


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


    public void logout(final @NonNull Context context) {
//        final ProgressDialog dialog = ProgressDialog.show(context,
//                context.getString(R.string.logout_ing), "");
//        if (SQLite.select().from(PushData.class).queryList().size() > 0) {
//            SyncService.syncImmediately(context, new SyncManager.SyncResultListener() {
//                @Override
//                public void onSuccess() {
//                    dialog.dismiss();
//                    doLogout(context);
//                }
//
//                @Override
//                public void onFailure() {
//                    dialog.dismiss();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context)
//                            .setTitle(R.string.logout_warning_with_data_not_sync)
//                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    doLogout(context);
//                                }
//                            })
//                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            });
//                    builder.create().show();
//                }
//            });
//        } else {
//        }
        doLogout(context);
    }

    private void doLogout(final @NonNull Context context) {
        userPrefs.clear();
        SQLite.delete().from(PushData.class).execute();
        SQLite.delete().from(Diary.class).execute();
        context.startActivity(SignupActivity.createIntent(context));
    }
}
