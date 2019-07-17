package com.example.adela.proiectquizz.profesori;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adela.proiectquizz.R;
import com.example.adela.proiectquizz.db.QuizContract;
import com.example.adela.proiectquizz.db.QuizDbHelper;
import com.example.adela.proiectquizz.pojos.Test;

import java.util.List;

public class CreatedTestsAdapter extends ArrayAdapter<Test> {

    private List<Test> dataSet;
    Context mContext;
    private SQLiteDatabase db;

    // View lookup cache
    private static class ViewHolder {
        TextView testName;
        TextView testClass;
        ImageView delete;
    }

    public CreatedTestsAdapter(List<Test> data, Context context, SQLiteDatabase db) {
        super(context, R.layout.created_test_item, data);
        this.dataSet = data;
        this.mContext=context;
        this.db = db;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        Test test = dataSet.get(position);
        ViewHolder viewHolder;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.created_test_item, parent, false);
            viewHolder.testName = (TextView) convertView.findViewById(R.id.testNameTv);
            viewHolder.testClass = (TextView) convertView.findViewById(R.id.testClassTv);
            viewHolder.delete = (ImageView) convertView.findViewById(R.id.deleteTestIv);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.testName.setText(test.getTestName());
        viewHolder.testClass.setText(test.getTestClass());
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(position);
            }
        });

        return convertView;
    }

    private void removeItem(int position) {
        String selection = QuizContract.TestsTable._ID + " LIKE ?";
        String[] selectionArgs = { dataSet.get(position).getId()+"" };
        db.delete(QuizContract.TestsTable.TABLE_NAME, selection, selectionArgs);

        dataSet.remove(position);
        notifyDataSetChanged();
    }
}
