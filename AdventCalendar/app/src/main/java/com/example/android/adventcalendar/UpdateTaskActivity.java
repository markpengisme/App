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
import android.view.View;
import android.widget.Button;
import java.util.Calendar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.adventcalendar.data.TaskContract;


public class UpdateTaskActivity extends AppCompatActivity {


    //dataId,dataDescription
    public String dataId;
    public String dataDescription;


    /**
     * setView
     * 取得bundle的dataId,dataDescription
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Bundle bundle =this.getIntent().getExtras();
        Button mButton=(Button)findViewById(R.id.addButton);
        mButton.setText("UPGRADE");
        dataId = bundle.getString("id");
        dataDescription = bundle.getString("description");
        EditText mEditText=(EditText) findViewById(R.id.editTextTaskDescription);
        mEditText.setText(dataDescription);
    }


    /**
     *  返回鈕
     */
    public void backButton(View view){
        Intent addTaskIntent = new Intent(UpdateTaskActivity.this, MainActivity.class);
        startActivity(addTaskIntent);
    }

    /**
     * onClickAddTask is called when the "ADD" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onClickAddTask(View view) {

        //日期選擇器
        DatePicker datePicker = (DatePicker) findViewById(R.id.DatePicker);
        int year = datePicker.getYear();
        int month = datePicker.getMonth() + 1;//Month is 0~11 so plus one
        int day = datePicker.getDayOfMonth();

        //留著原本的Description
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();

        //日曆取現在時間
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), 0, 0, 0);

        //轉成秒給timeInMills
        long timeInMills = calendar.getTimeInMillis();

        //Description不可為空
        if (input.length() == 0) {
            Toast.makeText(getBaseContext(), "請輸入內容", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         把資料用ContentResolver update進去
         */
        // Insert new task data via a ContentResolver
        // Create new empty ContentValues object
        ContentValues contentValues = new ContentValues();
        // Put the task description and selected mPriority into the ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_YEAR, year);
        contentValues.put(TaskContract.TaskEntry.COLUMN_MONTH, month);
        contentValues.put(TaskContract.TaskEntry.COLUMN_DAY, day);
        contentValues.put(TaskContract.TaskEntry.COLUMN_TIME_IN_MILLS, timeInMills);

        // Insert the content values via a ContentResolver
        Uri uri = TaskContract.TaskEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(dataId).build();
        int success = getContentResolver().update(uri, contentValues, null, null);

        // Display the URI that's returned with a Toast
        if (success == 0) {
            Toast.makeText(getBaseContext(), "更新失敗", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "更新成功", Toast.LENGTH_SHORT).show();

            // Finish activity (this returns back to MainActivity)
            finish();

        }


        /*
         * onPrioritySelected is called whenever a priority button is clicked.
         * It changes the value of mPriority based on the selected button.
         */

    }
}
