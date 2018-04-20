package com.trying.developing.taskarrangement.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.adapter.TasksAdapter;
import com.trying.developing.taskarrangement.model.Tasks;

import java.util.ArrayList;
import java.util.List;

public class AllTasksActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDatabase;
    private DatabaseReference mTasksDatabase;
    private FirebaseDatabase FDB;
    private RecyclerView recyclerView;
    private TasksAdapter adapter;
    private List<Tasks>tasksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        setTitle(getString(R.string.TasksTitle));

        mAuth = FirebaseAuth.getInstance();



        fab=(FloatingActionButton) findViewById(R.id.fabTasksId);

        recyclerView=(RecyclerView) findViewById(R.id.RecyclerView);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));


        tasksList=new ArrayList<>();
        adapter=new TasksAdapter(tasksList,getApplicationContext());

        FDB=FirebaseDatabase.getInstance();

        getAllTasks();




    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String current_id=currentUser.getUid();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("users").child(current_id);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String position =dataSnapshot.child("positionLevel").getValue().toString();
                if (position.equals("Team Leader")){
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(AllTasksActivity.this,CreateTaskActivity.class);

                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getAllTasks(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String currentid=currentUser.getUid();
        mTasksDatabase=FDB.getReference("tasks").child(currentid);
        mTasksDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Tasks data=dataSnapshot.getValue(Tasks.class);
                tasksList.add(data);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);

         getMenuInflater().inflate(R.menu.menu,menu);
         return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.logoutBut){

             FirebaseAuth.getInstance().signOut();
             Intent intent=new Intent(this,MainActivity.class);
             startActivity(intent);
             finish();

         }
         return true;
    }

}
