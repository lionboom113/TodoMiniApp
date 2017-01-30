package com.example.tuanhma.todominiapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Common.Utils;
import Model.Priority;
import Model.Task;

import static android.R.attr.value;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    List<Task> todoList;
    ArrayAdapter itemAdapter;
    ListView lvItems;
    EditText txtEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtEdit = (EditText) findViewById(R.id.txtNewTask);
        initiateTaskList();
        itemAdapter = new ListAdapter(this,R.layout.custom_adapter,todoList);
        lvItems = (ListView) findViewById(R.id.lvMain);
        lvItems.setAdapter(itemAdapter);
        lvItems.setDivider(null);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(getBaseContext(), TaskDetailActivity.class);
                int taskId = todoList.get(position).getId();
                intent.putExtra("TaskId", taskId);
                startActivity(intent);
            }
        });
    }
    private void initiateTaskList(){
        todoList = Utils.readAll(getBaseContext());
    }
    public void onAddItem(View view) {
        Intent myIntent = new Intent(MainActivity.this, AddTaskActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
    private void readItems(){

    }
    private void writeItems(){

    }
}
