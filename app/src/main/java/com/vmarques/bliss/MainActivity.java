package com.vmarques.bliss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayList<Question> questions;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lvQuestionList);

        progress = new ProgressDialog(MainActivity.this);
        progress.setMessage(getResources().getString(R.string.loading));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();

        //Get questions from service
        ApiClient.get("/questions", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                progress.dismiss();
                parseResults(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("error ", responseString);
                progress.dismiss();
                Intent intent = new Intent(MainActivity.this, NoConnectionActivity.class);
                intent.putExtra("com.vmarques.bliss.hasHealth", false);
                startActivity(intent);
            }

        });

        //Go to details activity
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                intent.putExtra("com.vmarques.bliss.id", questions.get(i).getId());
                intent.putExtra("com.vmarques.bliss.url", questions.get(i).getImage_url());
                intent.putExtra("com.vmarques.bliss.question", questions.get(i).getQuestion());
                intent.putExtra("com.vmarques.bliss.choices", questions.get(i).getChoices());
                intent.putExtra("com.vmarques.bliss.date", questions.get(i).getPublished_at());

                startActivity(intent);
            }
        });


    }

    //Save service response in arraylist
    private void parseResults(JSONArray response) {
        questions = new ArrayList<>();

        try {

            Log.d("response ", response.toString());
            for (int i = 0; i < response.length(); i++) {

                JSONObject object = response.getJSONObject(i);

                Question q = new Question();
                q.setId(object.getInt("id"));
                q.setQuestion(object.getString("question"));
                q.setPublished_at(object.getString("published_at"));
                q.setChoices(object.getString("choices"));
                q.setImage_url(object.getString("image_url"));
                q.setThumb_url(object.getString("thumb_url"));

                questions.add(q);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        fillListView(questions);

    }

    //Set listview adapter
    private void fillListView(ArrayList<Question> questions) {

        QuestionAdapter adapter = new QuestionAdapter(this, questions);

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
