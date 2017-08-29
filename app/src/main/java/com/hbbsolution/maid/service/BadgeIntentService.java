package com.hbbsolution.maid.service;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgeIntentService extends IntentService {

    public BadgeIntentService() {
        super("BadgeIntentService");
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int badgeCount = intent.getIntExtra("badgeCount", 0);
            boolean success = ShortcutBadger.applyCount(getApplicationContext(), badgeCount);
            Toast.makeText(BadgeIntentService.this, "Set count=" + badgeCount + ", success=" + success, Toast.LENGTH_SHORT).show();
        }
    }
}
