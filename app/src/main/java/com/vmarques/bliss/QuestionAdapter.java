package com.vmarques.bliss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class QuestionAdapter extends BaseAdapter {


        private Context context;
        private ArrayList<Question> question;

        public QuestionAdapter(Context context, ArrayList<Question> question) {
            this.context = context;
            this.question = question;
        }

        @Override
        public int getCount() {
            return question.size();
        }

        @Override
        public Object getItem(int position) {
            return question.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = null;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                v = inflater.inflate(R.layout.cell_question_list, parent, false);

            } else {

                v = convertView;
            }

            TextView tvQuestionName = (TextView) v.findViewById(R.id.tvQuestionName);
            TextView tvQuestionNumber = (TextView) v.findViewById(R.id.tvQuestionNumber);
            TextView tvDateCreated = (TextView) v.findViewById(R.id.tvDateCreated);
            ImageView ivThumb = (ImageView) v.findViewById(R.id.ivThumb);
//            ToggleButton tbFavourite = (ToggleButton) v.findViewById(R.id.tbAddFavourite);

            Question q = question.get(position);

                String thumbUrl = q.getThumb_url();
                Picasso.with(this.context).load(thumbUrl).placeholder(R.mipmap.ic_launcher).into(ivThumb);


            tvQuestionName.setText(q.getQuestion());
            tvDateCreated.setText(q.getPublished_at().toString());
            tvQuestionNumber.setText(q.getId().toString());


            return v;
        }


    }


