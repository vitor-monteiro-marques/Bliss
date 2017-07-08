package com.vmarques.bliss;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(MyUtils.isOnline(this)) {
            checkServerHealth();
        } else {
            Intent intent = new Intent(SplashActivity.this, NoConnectionActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void checkServerHealth() {

        ApiClient.get("/health", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Intent intent = new Intent(SplashActivity.this, NoConnectionActivity.class);
                intent.putExtra("com.vmarques.bliss.hasHealth", false);
                startActivity(intent);
                Log.d("error ", responseString);
                finish();

            }
        });

    }
}
