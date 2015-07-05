package com.example.sergio.caminando.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by sergio on 1/05/15.
 */
public class CaminandoSyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static CaminandoSyncAdapter sQuemedejesSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("QuemedejesSyncService", "onCreate - QuemedejesSyncService");
        synchronized (sSyncAdapterLock) {
            if (sQuemedejesSyncAdapter == null) {
                sQuemedejesSyncAdapter = new CaminandoSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sQuemedejesSyncAdapter.getSyncAdapterBinder();
    }
}
