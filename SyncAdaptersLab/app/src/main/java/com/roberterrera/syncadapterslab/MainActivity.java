package com.roberterrera.syncadapterslab;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String AUTHORITY = "com.roberterrera.syncadapterslab.StubProvider";
    private static final String ACCOUNT_TYPE = "example.com";
    private static final String ACCOUNT = "default_account";

    Account mAccount;
    Button mSyncButton;
    ContentResolver mResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccount = createSyncAccount(this);

        Button manualSyncButton = (Button) findViewById(R.id.manual_button);
        manualSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
            }
        });

        // Sync data every 2 minutes.
        Button twoMinSyncButton = (Button) findViewById(R.id.auto_two_button);
        twoMinSyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
                ContentResolver.addPeriodicSync(
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
                ContentResolver.setSyncAutomatically(mAccount, AUTHORITY, true);
                ContentResolver.addPeriodicSync(
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

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
          /*
           * If you don't set android:syncable="true" in
           * in your <provider> element in the manifest,
           * then call context.setIsSyncable(account, AUTHORITY, 1)
           * here.
           */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }

        return newAccount;
    }
    }
}
