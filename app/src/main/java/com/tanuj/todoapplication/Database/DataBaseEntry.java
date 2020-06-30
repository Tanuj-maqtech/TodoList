package com.tanuj.todoapplication.Database;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Tanuj Yadav on 21/04/17.
 */

public class DataBaseEntry {

    public static final String todoList = "todoList";
    public static final String id = "id";
    public static final String title =  "title";
    public static final String description =  "description";

    /**
     * getting current time and day
     *
     * @return
     */
    public static String getCurrentTime() {
        String delegate = "EEE hh:mm aaa";
        return (String) DateFormat.format(delegate, Calendar.getInstance().getTime());
    }


}
