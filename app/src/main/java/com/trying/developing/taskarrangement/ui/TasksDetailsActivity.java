package com.trying.developing.taskarrangement.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Tasks;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksDetailsActivity extends AppCompatActivity {

    private Tasks tasks;
    @BindView(R.id.TasksFromDetailsid)
     TextView taskFrom;
    @BindView(R.id.taskDscDetails)
     TextView taskDsc;
    @BindView(R.id.TaskNameDetailsid)
     TextView taskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_details);


        setTitle("Tasks Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){

            Intent widgetIntent = new Intent(TasksDetailsActivity.this, AppWidget.class);
            widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            int[] widgetIds = AppWidgetManager.getInstance(TasksDetailsActivity.this).
                    getAppWidgetIds(new ComponentName(TasksDetailsActivity.this, AppWidget.class));
            widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, widgetIds);
            TasksDetailsActivity.this.sendBroadcast(widgetIntent);

            tasks=(Tasks) bundle.getSerializable("choosenTask");
            ButterKnife.bind(this);

            taskFrom.setText(tasks.getLeaderEmail());
            taskDsc.setText(tasks.getTaskDsc());
            taskName.setText(tasks.getTaskName());

        }
    }
}
