package Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tuanhma.todominiapp.TaskDBHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Model.Status;
import Model.Task;

/**
 * Created by TuanHMA on 1/29/2017.
 */

public class Utils {
    public static void saveEntityToDB(Task task, Context context) {
        TaskDBHelper dbHelper = new TaskDBHelper(context);
        SQLiteDatabase writeDB = dbHelper.getWritableDatabase();
        ContentValues newTask = new ContentValues();
        newTask.put("taskname", task.getTaskName());
        newTask.put("description", task.getTaskDescription());
        newTask.put("duedate", task.getEndDate().getTime());
        newTask.put("type",task.getTaskType());
        newTask.put("priority", task.getPrority());
        newTask.put("status", Status.TODO);
        writeDB.insert("tblTask", null, newTask);
    }

    public static List<Task> readAll(Context context) {
        TaskDBHelper dbHelper = new TaskDBHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "taskname",
                "description",
                "startdate",
                "duedate",
                "type",
                "status",
                "priority"
        };
        String selection = "status = ?";

        String[] selectionArgs = { "" + Status.TODO };

        Cursor cursor = readDB.query(
                "tblTask",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        List items = new ArrayList<Task>();
        cursor.getColumnNames();
        while(cursor.moveToNext()) {
            long taskId = cursor.getLong(
                    cursor.getColumnIndexOrThrow("id"));
            String taskName = cursor.getString(
                    cursor.getColumnIndexOrThrow("taskname"));
            String taskDescription  = cursor.getString(
                    cursor.getColumnIndexOrThrow("description"));
            Date duedate = new Date(cursor.getLong(
                    cursor.getColumnIndexOrThrow("duedate")));
            Task task = new Task(taskName);
            task.setTaskDescription(taskDescription);
            task.setEndDate(duedate);
            task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")));
            task.setPrority(cursor.getInt(cursor.getColumnIndexOrThrow("priority")));
            task.setTaskType(cursor.getInt(cursor.getColumnIndexOrThrow("type")));
            task.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            items.add(task);
        }
        cursor.close();
        return items;
    }
    public static Task findTaskById(Context context, int taskId) {
        TaskDBHelper dbHelper = new TaskDBHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "taskname",
                "description",
                "startdate",
                "duedate",
                "type",
                "status",
                "priority"
        };
        String selection = "id = ?";

        String[] selectionArgs = { "" + taskId };

        Cursor cursor = readDB.query(
                "tblTask",                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        List items = new ArrayList<Task>();
        cursor.getColumnNames();
        if(cursor.moveToNext()) {
            String taskName = cursor.getString(
                    cursor.getColumnIndexOrThrow("taskname"));
            String taskDescription  = cursor.getString(
                    cursor.getColumnIndexOrThrow("description"));
            Date duedate = new Date(cursor.getLong(
                    cursor.getColumnIndexOrThrow("duedate")));
            Task task = new Task(taskName);
            task.setTaskDescription(taskDescription);
            task.setEndDate(duedate);
            task.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")));
            task.setPrority(cursor.getInt(cursor.getColumnIndexOrThrow("priority")));
            task.setTaskType(cursor.getInt(cursor.getColumnIndexOrThrow("type")));
            task.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            items.add(task);
            return task;
        }
        cursor.close();
        return null;
    }
    public static void updateEntityToDB(Task task, Context context) {
        TaskDBHelper dbHelper = new TaskDBHelper(context);
        SQLiteDatabase readDB = dbHelper.getReadableDatabase();
        ContentValues newTask = new ContentValues();
        newTask.put("taskname", task.getTaskName());
        newTask.put("description", task.getTaskDescription());
        newTask.put("duedate", task.getEndDate().getTime());
        newTask.put("type",task.getTaskType());
        newTask.put("priority", task.getPrority());
        newTask.put("status", task.getStatus());

        String selection = "id = ?";
        String[] selectionArgs = { "" + task.getId() };

        int count = readDB.update(
                "tblTask",
                newTask,
                selection,
                selectionArgs);
    }
}
