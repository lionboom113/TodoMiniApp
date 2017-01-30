package com.example.tuanhma.todominiapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

import Model.Priority;
import Model.Status;
import Model.Task;
import Model.TaskType;

/**
 * Created by TuanHMA on 1/27/2017.
 */

public class ListAdapter extends ArrayAdapter<Task> {

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, List<Task> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_adapter, null);
        }

        Task p = getItem(position);

        if (p != null) {
            TextView taskName = (TextView) v.findViewById(R.id.taskName);
            TextView taskDescription = (TextView) v.findViewById(R.id.taskDescription);
            TextView dueDate = (TextView) v.findViewById(R.id.dueDate);
            TextView priority = (TextView) v.findViewById(R.id.priority);
            if (taskName != null) {
                taskName.setText(p.getTaskName());
            }

            if (taskDescription != null) {
                taskDescription.setText(p.getTaskDescription());
            }

            if (dueDate != null) {
                Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                dueDate.setText(formatter.format(p.getEndDate()));
            }
            String txtPriority = "N/A";
            switch (p.getPrority()) {
                case Priority.Low:
                    txtPriority = "Low";
                    priority.setTextColor(Color.parseColor("#2196F3"));
                    break;
                case Priority.Medium:
                    txtPriority = "Medium";
                    priority.setTextColor(Color.parseColor("#8BC34A"));
                    break;
                case Priority.High:
                    txtPriority = "High";
                    priority.setTextColor(Color.parseColor("#FF9800"));
                    break;
            }
            ImageView iv = (ImageView)v.findViewById(R.id.taskLogo);
            switch (p.getTaskType()){
                case TaskType.TODO:
                    iv.setImageResource(R.drawable.todo);
                    break;
                case TaskType.MEETING:
                    iv.setImageResource(R.drawable.event);
                    break;
            }
            priority.setText(txtPriority);
            TextView status = (TextView) v.findViewById(R.id.txtStatus);
            switch (p.getStatus()) {
                case Status.TODO:
                    status.setText("Todo");
                    status.setTextColor(Color.parseColor("#2196F3"));
                    break;
                case Status.DONE:
                    status.setText("Done");
                    status.setTextColor(Color.parseColor("#757575"));
                    break;
                case Status.CANCEL:
                    status.setText("Cancelled");
                    status.setTextColor(Color.parseColor("#757575"));
                    break;
                case Status.PENDING:
                    status.setText("Pending");
                    status.setTextColor(Color.parseColor("#757575"));
                    break;
            }
        }

        return v;
    }

}
