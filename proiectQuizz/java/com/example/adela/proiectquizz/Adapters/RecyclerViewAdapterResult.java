package com.example.adela.proiectquizz.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.adela.proiectquizz.pojos.Student;
import com.example.adela.proiectquizz.pojos.Question;
import com.example.adela.proiectquizz.R;

public class RecyclerViewAdapterResult extends ArrayAdapter<Student> implements View.OnClickListener{

    private ArrayList<Student> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtPoints;
        TextView txtType;
    }

    public RecyclerViewAdapterResult(ArrayList<Student> data, Context context) {
        super(context, R.layout.item_adapter_result, data);
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

        Student dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        RecyclerViewAdapterResult.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new RecyclerViewAdapterResult.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_adapter_result, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.tv_type);
            viewHolder.txtPoints = (TextView) convertView.findViewById(R.id.tv_points);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RecyclerViewAdapterResult.ViewHolder) convertView.getTag();
            result=convertView;
        }

        viewHolder.txtName.setText( String.valueOf(position + 1)+ "." + dataModel.getName());
        viewHolder.txtPoints.setText( dataModel.getPoints() + "p");
        if(dataModel.isType()){
            viewHolder.txtType.setText("Admis");
            viewHolder.txtType.setTextColor(Color.GREEN);
        }else{
            viewHolder.txtType.setText("Respins");
            viewHolder.txtType.setTextColor(Color.RED);
        }
        return convertView;
    }
}
