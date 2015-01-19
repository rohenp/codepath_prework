package com.samples.rohen.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class ItemArrayAdapter extends ArrayAdapter<TodoItem> {

    public ItemArrayAdapter(Context context, ArrayList<TodoItem> todoItems) {
        super(context, 0, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.todo_item, parent,
                    false);
        }

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);

        tvName.setTextColor(Color.WHITE);
        tvPriority.setTextColor(Color.WHITE);
        tvDueDate.setTextColor(Color.WHITE);

        // Populate the data into the template view using the data object
        tvName.setText(todoItem.getName());
        tvPriority.setText("P" + String.valueOf(todoItem.getPriority()));

        String dateString = todoItem.getDateDue();
        if (dateString.length() > 0) {
            tvDueDate.setText(dateString);

            Date date = new Date();
            int now = (int) (date.getTime() / 1000L);

            //Due in the next week
            if(todoItem.getTimeDue() <= (now + 604800) || todoItem.getTimeDue() < now) {
                convertView.setBackgroundColor(Color.RED);
            //Due in the next month
            } else if (todoItem.getTimeDue() <= (now + 2419200)){
                convertView.setBackgroundColor(Color.BLUE);
            } else {
                convertView.setBackgroundColor(Color.BLACK);
            }
        } else {
            tvDueDate.setText("-");
            convertView.setBackgroundColor(Color.GRAY);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
