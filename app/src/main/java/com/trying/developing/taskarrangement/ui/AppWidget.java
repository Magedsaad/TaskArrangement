package com.trying.developing.taskarrangement.ui;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Tasks;
import com.trying.developing.taskarrangement.model.contentprovider.TaskContract;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);


        Tasks tasks=null;
        ContentResolver contentResolver=context.getContentResolver();
        Uri tasksUri = TaskContract.TaskEntry.CONTENT_URI;
        Cursor tasksCursor=contentResolver.query(tasksUri,null,null,null,null);
        if(tasksCursor != null && tasksCursor.moveToNext()){
            tasks=new Tasks();
            tasks.setTaskName(tasksCursor.getString(tasksCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_Task_Name)));
            tasks.setLeaderEmail(tasksCursor.getString(tasksCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_Task_TEAM_LEADER)));
            tasks.setTaskDsc(tasksCursor.getString(tasksCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_Task_Description)));
            tasks.setMemberEmail(tasksCursor.getString(tasksCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_Task_Member)));
            tasksCursor.close();

        }

        if(tasks !=null){

            views.setTextViewText(R.id.TasksFromWidgetId, tasks.getLeaderEmail());
            views.setTextViewText(R.id.TasksDscWidgetId, tasks.getTaskDsc());


        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

