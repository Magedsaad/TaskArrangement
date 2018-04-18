package com.trying.developing.taskarrangement.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Tasks;

public class TasksDetailsActivity extends AppCompatActivity {

    private Tasks tasks;
    private TextView taskFrom;
    private TextView taskDsc;
    private TextView taskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_details);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            tasks=(Tasks) bundle.getSerializable("choosenTask");
            taskFrom=(TextView) findViewById(R.id.TasksFromDetailsid);
            taskDsc=(TextView) findViewById(R.id.taskDscDetails);
            taskName=(TextView) findViewById(R.id.TaskNameDetailsid);


            taskFrom.setText(tasks.getLeaderEmail());
            taskDsc.setText(tasks.getTaskDsc());
            taskName.setText(tasks.getTaskName());

        }
    }
}
