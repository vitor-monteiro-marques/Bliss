package com.vmarques.bliss;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private FloatingActionButton fab;
    private ProgressDialog progress;
    private String inputText, url, questionName, choices, date, time;
    private TextView tvQuestionName, tvChoices, tvResults, tvDate;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Get views
        imageView = (ImageView) findViewById(R.id.ivCoverImage);
        tvQuestionName = (TextView) findViewById(R.id.tvQuestionName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvChoices = (TextView) findViewById(R.id.tvChoices);
        tvResults = (TextView) findViewById(R.id.tvResults);

        progress = new ProgressDialog(DetailsActivity.this);
        progress.setMessage(getResources().getString(R.string.sending));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);


        //Get data from activity
        id = getIntent().getIntExtra("com.vmarques.bliss.id", 0);
        url = getIntent().getStringExtra("com.vmarques.bliss.url");
        questionName = getIntent().getStringExtra("com.vmarques.bliss.question");
        choices = getIntent().getStringExtra("com.vmarques.bliss.choices");
        date = MyUtils.getDateFromString(getIntent().getStringExtra("com.vmarques.bliss.date"));
        time = MyUtils.getTimeFromString(getIntent().getStringExtra("com.vmarques.bliss.date"));

        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);
        tvQuestionName.setText(questionName);
        tvDate.setText(date + " " + time);

        //Display choices
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(choices);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);
                String choice = (String) obj.get("choice");
                Integer result = (Integer) obj.get("votes");
                tvChoices.append(choice + "\n");
                tvResults.append(result + "\n");
                Log.d("c", obj.get("choice").toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        fab = (FloatingActionButton) this.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    //Input email dialog
    private void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog dialog;
        builder.setTitle(getResources().getString(R.string.enter_email));

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                inputText = input.getText().toString();
                sendEmail();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        builder.show();

    }

    //Send email
    private void sendEmail() {
        progress.show();

        RequestParams params = new RequestParams();
        params.put("?", inputText);
        params.put("&", questionName);

        ApiClient.post("/share", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(DetailsActivity.this, getResources().getString(R.string.email_sent), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Toast.makeText(DetailsActivity.this, getResources().getString(R.string.email_error), Toast.LENGTH_LONG).show();
                Log.d("error ", responseString);

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progress.dismiss();
            }
        });

    }
}
