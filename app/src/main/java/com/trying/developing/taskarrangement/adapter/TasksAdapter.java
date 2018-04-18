package com.trying.developing.taskarrangement.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Tasks;
import com.trying.developing.taskarrangement.model.contentprovider.TaskContract;
import com.trying.developing.taskarrangement.ui.AllTasksActivity;
import com.trying.developing.taskarrangement.ui.TasksDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by developing on 4/18/2018.
 */

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    List<Tasks> data = new ArrayList<>();
    Context mContext;
    Tasks tasks;

    public TasksAdapter(List<Tasks> data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskslistitem, parent, false);

        return new TasksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {

        Tasks tasks = data.get(position);
        holder.tasksFrom.setText("From: " + tasks.getLeaderEmail());
        holder.tasksName.setText("TaskName: " + tasks.getTaskName());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TasksViewHolder extends RecyclerView.ViewHolder {

        private TextView tasksFrom;
        private TextView tasksName;

        public TasksViewHolder(View itemView) {
            super(itemView);
            tasksFrom = (TextView) itemView.findViewById(R.id.tasksFromlistid);
            tasksName = (TextView) itemView.findViewById(R.id.tasksNamelistid);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tasks = data.get(getAdapterPosition());
                    new HandlTask().execute();


                }
            });
        }
    }

    public class HandlTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            ContentResolver contentResolver = mContext.getContentResolver();
            Uri tasksUri = TaskContract.TaskEntry.CONTENT_URI;
            ContentValues values = new ContentValues();
            values.put(TaskContract.TaskEntry.COLUMN_Task_Name, tasks.getTaskName());
            values.put(TaskContract.TaskEntry.COLUMN_Task_Description, tasks.getTaskDsc());
            values.put(TaskContract.TaskEntry.COLUMN_Task_Member, tasks.getMemberEmail());
            values.put(TaskContract.TaskEntry.COLUMN_Task_TEAM_LEADER, tasks.getLeaderEmail());
            contentResolver.insert(tasksUri, values);

            Intent intent = new Intent(mContext.getApplicationContext(), TasksDetailsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle bundle = new Bundle();

            bundle.putSerializable("choosenTask", tasks);
            intent.putExtras(bundle);
            mContext.startActivity(intent);


            return null;
        }
    }
}
