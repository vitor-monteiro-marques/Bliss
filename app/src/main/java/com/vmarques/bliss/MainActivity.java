package com.vmarques.bliss;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    private ListView lv;
    private ArrayList<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lvQuestionList);
//        Creating an object of our api interface
//        ApiService api = ApiClient.getApiService();
//
//        /**
//         * Calling JSON
//         */
//        Call<QuestionList> call = api.getAllQuestions();
//
//        call.enqueue(new Callback<QuestionList>() {
//            @Override
//            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
//                Gson gson = new GsonBuilder().create();
//
//                Log.d("cenas: ", response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<QuestionList> call, Throwable t) {
//                Log.d("error: ", t.toString());
//            }
//        });



        ApiClient.get("/health", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d("resp ", response.toString());

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("resp ", response.toString());
                Log.d("code ", statusCode+"");

                ApiClient.get("/questions", null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        super.onSuccess(statusCode, headers, response);

                        parseResults(response);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.d("error ", responseString);
                    }


                });



            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);

                progress = new ProgressDialog(MainActivity.this);
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.show();

            }

            @Override
            public void onFinish() {
                super.onFinish();
                progress.hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("error ", responseString);
            }
        });



    }

    private void parseResults(JSONArray response) {
        questions = new ArrayList<>();

        try {
          //JSONObject joParent = response.getJSONObject(0);
            //JSONArray jsonArray = new JSONArray(response);
            Log.d("response ", response.toString());
            for (int i = 0; i < response.length(); i++) {

                JSONObject object = response.getJSONObject(i);
//                JSONObject object = (JSONObject) joParent.get(i);

                Question q = new Question();
                q.setId(object.getInt("id"));
                q.setQuestion(object.getString("question"));
                q.setPublished_at(object.getString("published_at"));
                q.setChoices(object.getJSONArray("choices"));
                q.setImage_url(object.getString("image_url"));
                q.setThumb_url(object.getString("thumb_url"));

                questions.add(q);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        fillListView(questions);

    }

    private void fillListView(ArrayList<Question> questions) {

        QuestionAdapter adapter = new QuestionAdapter(this, questions);

        lv.setAdapter(adapter);

    }

}
