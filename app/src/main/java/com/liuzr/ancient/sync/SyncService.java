
package com.liuzr.ancient.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;


public class SyncService extends IntentService {

    private static SyncResultListener mSyncResultListener;

    public SyncService() {
        super("SyncService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    public static void syncImmediately(Context context) {
        syncImmediately(context, null);
    }

    public static void syncImmediately(Context context, SyncResultListener syncResultListener) {
        mSyncResultListener = syncResultListener;
        context.startService(new Intent(context, SyncService.class));
    }

    public interface SyncResultListener {
        void onSuccess();

        void onFailure();
    }

}
