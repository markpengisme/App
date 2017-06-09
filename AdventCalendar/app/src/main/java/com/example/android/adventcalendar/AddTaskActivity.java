/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.adventcalendar;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.adventcalendar.data.TaskContract;


public class AddTaskActivity extends AppCompatActivity {

    // Declare a member variable to keep track of a task's selected mPriority


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
    }


    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public  void backbutton(View view){
        Intent addTaskIntent = new Intent(AddTaskActivity.this, MainActivity.class);
        startActivity(addTaskIntent);
    }


    public void onClickAddTask(View view) {
        DatePicker datePicker =(DatePicker)findViewById(R.id.DatePicker) ;
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();


        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),0,0,0);
        long timeInMills = calendar.getTimeInMillis();
        if (input.length() == 0) {
            Toast.makeText(getBaseContext(),String.valueOf(timeInMills),Toast.LENGTH_SHORT).show();
            return;
        }


        int year=datePicker.getYear();
        int month=datePicker.getMonth()+1;//Month is 0~11 so plus one
        int day=datePicker.getDayOfMonth();



        // Insert new task data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_YEAR, year);
        contentValues.put(TaskContract.TaskEntry.COLUMN_MONTH, month);
        contentValues.put(TaskContract.TaskEntry.COLUMN_DAY, day);
        contentValues.put(TaskContract.TaskEntry.COLUMN_DAY, day);
        contentValues.put(TaskContract.TaskEntry.COLUMN_TIME_IN_MILLS, timeInMills);
        // Insert the content values via a ContentResolver
        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

        // Display the URI that's returned with a Toast
        // [Hint] Don't forget to call finish() to return to MainActivity after this insert is complete
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        // Finish activity (this returns back to MainActivity)
        finish();

    }


    /**
     * onPrioritySelected is called whenever a priority button is clicked.
     * It changes the value of mPriority based on the selected button.
     */


}
