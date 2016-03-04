package com.roberterrera.syncadapterslab;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.roberterrera.syncadapterslab.StubProvider";
    private static final String ACCOUNT_TYPE = "roberterrera.com";
    private static final String ACCOUNT = "default_account";

    Account mAccount;
    ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccount = createSyncAccount(this);
        mResolver = getContentResolver();

        Button manualSyncButton = (Button) findViewById(R.id.manual_button);
        manualSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
<<<<<<< HEAD
                ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
=======
                mResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
                Log.d("MainActivity", "Gets here");

                Toast.makeText(MainActivity.this, "Method complete.", Toast.LENGTH_SHORT).show();
>>>>>>> d724924a8a03ca6a496f8c46020fc24c17362cec
            }
        });

        // Sync data every 2 minutes.
        Button twoMinSyncButton = (Button) findViewById(R.id.auto_two_button);
        twoMinSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
                mResolver.addPeriodicSync(
                        mAccount,
                        AUTHORITY,
                        Bundle.EMPTY,
                        120);
            }
        });

        Button fiveMinSyncButton = (Button) findViewById(R.id.auto_five_button);
        fiveMinSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
                mResolver.addPeriodicSync(
                        mAccount,
                        AUTHORITY,
                        Bundle.EMPTY,
                        300);
            }
        });
    }

    // For authenticating an account.
    public static Account createSyncAccount(Context context){
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

//        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
//        } else {}

        return newAccount;
    }
}
