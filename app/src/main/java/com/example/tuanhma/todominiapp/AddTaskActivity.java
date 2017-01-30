package com.example.tuanhma.todominiapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Calendar;
import java.util.Date;

import Common.Utils;
import Model.Priority;
import Model.Task;
import Model.TaskType;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        DatePicker dueDate = (DatePicker) findViewById(R.id.dtpDueDate);
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        dueDate.updateDate(mYear,mMonth,mDay);
    }

    public void onAddItem(View view) {
        EditText taskName = (EditText)findViewById(R.id.txtNewTask);
        EditText taskDescription = (EditText)findViewById(R.id.txtTaskDescription);
        Task task = new Task(taskName.getText().toString());
        task.setTaskDescription(taskDescription.getText().toString());
        DatePicker dueDate = (DatePicker) findViewById(R.id.dtpDueDate);
        RadioGroup priority = (RadioGroup) findViewById(R.id.rdoPriority);
        switch (priority.getCheckedRadioButtonId()){
            case R.id.priority_low:
                task.setPrority(Priority.Low);
                    break;
            case R.id.priority_normal:
                task.setPrority(Priority.Medium);
                    break;
            case R.id.priority_high:
                task.setPrority(Priority.High);
                break;
        }
        RadioGroup taskType = (RadioGroup) findViewById(R.id.rdoTaskType);
        switch (taskType.getCheckedRadioButtonId()){
            case R.id.type_meeting:
                task.setTaskType(TaskType.MEETING);
                break;
            case R.id.type_todo:
                task.setTaskType(TaskType.TODO);
                break;
        }
        task.setStartDate(new Date());
        task.setEndDate(getDateFromDatePicker(dueDate));
        Utils.saveEntityToDB(task, getBaseContext());

        Intent myIntent = new Intent(AddTaskActivity.this, MainActivity.class);
        AddTaskActivity.this.startActivity(myIntent);
    }
    private Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
