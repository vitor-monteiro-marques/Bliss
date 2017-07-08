package com.vmarques.bliss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NoConnectionActivity extends AppCompatActivity {

    private boolean hasHealth;
    private TextView textView;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_connection);

        textView = (TextView) findViewById(R.id.textView);
        hasHealth = getIntent().getBooleanExtra("com.vmarques.bliss.hasHealth", true);

        //Change textview message
         if(!hasHealth) {
             textView.setText(getResources().getString(R.string.no_health));
         } else {
             textView.setText(getResources().getString(R.string.no_connection));
         }

    }

    //Check connection on btn press
    public void onCheckConnection(View v) {

        progress = new ProgressDialog(NoConnectionActivity.this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        if(MyUtils.isOnline(this)) {
            progress.dismiss();
            Intent intent = new Intent(NoConnectionActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            progress.dismiss();
        }
    }
}
