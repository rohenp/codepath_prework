package com.samples.rohen.simpletodo;

import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;

public class TodoItem {
    private int id;
    private String name;
    private int priority;
    private int time_due;

    public TodoItem(String name, int priority, int time_due) {
        super();
        this.name = name;
        this.priority = priority;
        this.time_due = time_due;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTimeDue() {
        return time_due;
    }

    public void setTimeDue(int time_due) {
        this.time_due = time_due;
    }

    public String getDateDue() {
        if(time_due < 0){
            return "";
        }

        Date date = new Date(time_due * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        String dateString = sdf.format(date);

        return dateString;
    }

    public void setDateDue(String dateString) throws Exception {
        if (dateString.length() != 8) {
            throw new Exception("Invalid date");
        }

        dateString = dateString.replaceAll(".-", "/");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
        Date parsedDate = sdf.parse(dateString);

        time_due = (int) (parsedDate.getTime() / 1000L);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}