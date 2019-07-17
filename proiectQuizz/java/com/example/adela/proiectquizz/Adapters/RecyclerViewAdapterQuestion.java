package com.example.adela.proiectquizz.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.adela.proiectquizz.pojos.Question;
import com.example.adela.proiectquizz.R;

public class RecyclerViewAdapterQuestion extends ArrayAdapter<Question> implements View.OnClickListener{

    private ArrayList<Question> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtQuestion;
        TextView txtResponseA;
        TextView txtResponseB;
        TextView txtResponseC;
        TextView txtPoints;
    }

    public RecyclerViewAdapterQuestion(ArrayList<Question> data, Context context) {
        super(context, R.layout.item_adapter_questions, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Question dataModel=(Question) object;

        switch (v.getId())
        {
            case R.id.item_info:

                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Question dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_adapter_questions, parent, false);
            viewHolder.txtQuestion = (TextView) convertView.findViewById(R.id.tv_question);
            viewHolder.txtResponseA = (TextView) convertView.findViewById(R.id.tv_responseA);
            viewHolder.txtResponseB = (TextView) convertView.findViewById(R.id.tv_responseB);
            viewHolder.txtResponseC = (TextView) convertView.findViewById(R.id.tv_responseC);
            viewHolder.txtPoints = (TextView) convertView.findViewById(R.id.tv_points);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtQuestion.setText( String.valueOf(position + 1)+ "." + dataModel.getQuestion());
        viewHolder.txtResponseA.setText(dataModel.getOption1());
        viewHolder.txtResponseB.setText(dataModel.getOption2());
        viewHolder.txtResponseC.setText(dataModel.getOption3());
        viewHolder.txtPoints.setText(String.valueOf(dataModel.getAnswerNr()) + "p");
        return convertView;
    }
}
