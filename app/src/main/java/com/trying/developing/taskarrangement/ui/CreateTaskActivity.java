package com.trying.developing.taskarrangement.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Tasks;
import com.trying.developing.taskarrangement.model.contentprovider.TaskContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateTaskActivity extends AppCompatActivity {

    @BindView(R.id.MemberEmailid)
     EditText memberEmail;
    @BindView(R.id.TaskNameId)
     EditText tasksName;
    @BindView(R.id.TaskDscid)
     EditText taskDescription;
    private DatabaseReference mTasksDatabase;
    private DatabaseReference mUserDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        setTitle("Assign Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mTasksDatabase = FirebaseDatabase.getInstance().getReference();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");

    }

    public void assignTask(View view){
        final String mMemberEmail =memberEmail.getText().toString();
        final String mTasksName =tasksName.getText().toString();
        final String mTaskDsc=taskDescription.getText().toString();
        final String tasksFrom=user.getEmail();

        final Query userQuery = mUserDatabase.orderByChild("email").equalTo(memberEmail.getText().toString());

        userQuery.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (final DataSnapshot child : dataSnapshot.getChildren()) {

                    final String id = child.getKey();

                    mAuth = FirebaseAuth.getInstance();
                    user = mAuth.getCurrentUser();

                    final String current_id = user.getUid();
                    final Tasks tasks=new Tasks(tasksFrom,id,mMemberEmail,mTasksName,mTaskDsc);

                    mTasksDatabase.child("tasks").child(current_id).push().setValue(tasks).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            mAuth = FirebaseAuth.getInstance();
                            user = mAuth.getCurrentUser();
                            Tasks tasks=new Tasks(tasksFrom,id,mMemberEmail,mTasksName,mTaskDsc);
                            String id = child.getKey();
                            mTasksDatabase.child("tasks").child(id).push().setValue(tasks);

                            Intent intent=new Intent(CreateTaskActivity.this,TasksDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("choosenTask", tasks);
                            intent.putExtras(bundle);
                            startActivity(intent);

                            ContentResolver contentResolver = getContentResolver();
                            Uri tasksUri = TaskContract.TaskEntry.CONTENT_URI;
                            ContentValues values = new ContentValues();
                            values.put(TaskContract.TaskEntry.COLUMN_Task_Name, tasks.getTaskName());
                            values.put(TaskContract.TaskEntry.COLUMN_Task_Description, tasks.getTaskDsc());
                            values.put(TaskContract.TaskEntry.COLUMN_Task_Member, tasks.getMemberEmail());
                            values.put(TaskContract.TaskEntry.COLUMN_Task_TEAM_LEADER, tasks.getLeaderEmail());
                            contentResolver.insert(tasksUri, values);


                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
