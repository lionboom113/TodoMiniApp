package com.example.tuanhma.todominiapp;

import android.content.Intent;
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
import Model.Status;
import Model.Task;
import Model.TaskType;

public class TaskDetailActivity extends AppCompatActivity {
    EditText txtTaskName;
    EditText txtTaskDescription;
    DatePicker dtpDueDate;
    RadioGroup rdoStatus;
    RadioGroup rdoPriority;
    RadioGroup rdoTaskType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        Intent intent = getIntent();
        int taskId = intent.getIntExtra("TaskId", 0);
        Task task = Utils.findTaskById(getBaseContext(), taskId);
        txtTaskName = (EditText)findViewById(R.id.txtNewTask);
        txtTaskDescription = (EditText) findViewById(R.id.txtTaskDescription);
        dtpDueDate = (DatePicker) findViewById(R.id.dtpDueDate);
        rdoPriority = (RadioGroup) findViewById(R.id.rdoPriority);
        rdoTaskType = (RadioGroup) findViewById(R.id.rdoTaskType);
        rdoStatus = (RadioGroup) findViewById(R.id.rdoStatus);
        txtTaskName.setText(task.getTaskName());
        txtTaskDescription.setText(task.getTaskDescription());
        Calendar c = Calendar.getInstance();
        c.setTime(task.getEndDate());
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        dtpDueDate.updateDate(mYear,mMonth,mDay);
        switch (task.getTaskType()){
            case (TaskType.TODO):
                rdoStatus.check(R.id.type_todo);
                break;
            case (TaskType.MEETING):
                rdoStatus.check(R.id.type_meeting);
                break;
        }
        switch (task.getPrority()){
            case (Priority.High):
                rdoPriority.check(R.id.priority_high);
                break;
            case (Priority.Medium):
                rdoPriority.check(R.id.priority_normal);
                break;
            case (Priority.Low):
                rdoPriority.check(R.id.priority_low);
                break;
        }
        switch (task.getStatus()){
            case (Status.TODO):
                rdoStatus.check(R.id.status_todo);
                break;
            case (Status.CANCEL):
                rdoStatus.check(R.id.status_cancel);
                break;
            case (Status.DONE):
                rdoStatus.check(R.id.status_done);
                break;
        }

    }
    public void onUpdateItem(View view) {
        Task task = new Task(txtTaskName.getText().toString());
        task.setTaskDescription(txtTaskDescription.getText().toString());
        switch (rdoPriority.getCheckedRadioButtonId()){
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
        switch (rdoStatus.getCheckedRadioButtonId()){
            case R.id.status_cancel:
                task.setStatus(Status.CANCEL);
                break;
            case R.id.status_done:
                task.setStatus(Status.DONE);
                break;
            case R.id.status_todo:
                task.setStatus(Status.TODO);
                break;
        }
        switch (rdoTaskType.getCheckedRadioButtonId()){
            case R.id.type_meeting:
                task.setTaskType(TaskType.MEETING);
                break;
            case R.id.type_todo:
                task.setTaskType(TaskType.TODO);
                break;
        }
        task.setStartDate(new Date());
        task.setEndDate(getDateFromDatePicker(dtpDueDate));
        task.setId(getIntent().getIntExtra("TaskId", 0));
        Utils.updateEntityToDB(task, getBaseContext());

        Intent myIntent = new Intent(TaskDetailActivity.this, MainActivity.class);
        TaskDetailActivity.this.startActivity(myIntent);
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
