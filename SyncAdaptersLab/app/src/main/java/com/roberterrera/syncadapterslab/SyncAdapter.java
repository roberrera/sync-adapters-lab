package com.roberterrera.syncadapterslab;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ra on 3/2/16.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter{
    ContentResolver mContentResolver;

    private static final String TAG = SyncAdapter.class.getCanonicalName();
    private String bankofAmericaURL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=BAC&symbol=BBY&callback=myFunction";
    private String bestBuyURL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=BBY&symbol=BBY&callback=myFunction";
    private String yahooURL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=YHOO&symbol=BBY&callback=myFunction";
    private String twitterURL = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?symbol=TWTR&symbol=BBY&callback=myFunction";

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        getStock(bankofAmericaURL);
        getStock(bestBuyURL);
        getStock(yahooURL);
        getStock(twitterURL);
    }


    private void getStock(String stockUrl){
        String data = " ";
        try {
            URL url = new URL(stockUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            data = getInputData(inputStream);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        SearchResults results = gson.fromJson(data, SearchResults.class);
        String title = results.getResults().get(0).getName();
        String price = results.getResults().get(0).getLastPrice();
        Log.d(TAG, title + " , $" + price);
    }

    private String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        String data = null;
        while ((data = reader.readLine()) != null) {
            builder.append(data);
        }
        reader.close();
        return builder.toString();
    }
}
