package com.vmarques.bliss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView tvQuestionName, tvChoices, tvResults, tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        imageView = (ImageView) findViewById(R.id.ivCoverImage);
        tvQuestionName = (TextView) findViewById(R.id.tvQuestionName);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvChoices = (TextView) findViewById(R.id.tvChoices);
        tvResults = (TextView) findViewById(R.id.tvResults);

        //Get data from activity
        Integer id = getIntent().getIntExtra("com.vmarques.bliss.id", 0);
        String url = getIntent().getStringExtra("com.vmarques.bliss.url");
        String questionName = getIntent().getStringExtra("com.vmarques.bliss.question");
        String choices = getIntent().getStringExtra("com.vmarques.bliss.choices");
        String date = MyUtils.getDateFromString(getIntent().getStringExtra("com.vmarques.bliss.date"));
        String time = MyUtils.getTimeFromString(getIntent().getStringExtra("com.vmarques.bliss.date"));

        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher).into(imageView);
        tvQuestionName.setText(questionName);
        tvDate.setText(date + " " + time);

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


        Log.d("id", id.toString());
        Log.d("quest", choices);
        Log.d("date", date);
        Log.d("time", time);



    }
}
