package com.trying.developing.taskarrangement.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.adapter.ContactsAdapter;
import com.trying.developing.taskarrangement.model.Users;

import java.util.ArrayList;
import java.util.List;

public class list_all_contacts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;
    private DatabaseReference mUserDatabase;

    private List<Users> usersList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_contacts);


        usersList=new ArrayList<>();

         contactsAdapter=new ContactsAdapter(usersList,getApplicationContext());

        recyclerView=(RecyclerView) findViewById(R.id.contactList_id);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));

        getAllUsers();

    }


    public void getAllUsers(){

        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("users");
        mUserDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Users data=dataSnapshot.getValue(Users.class);
                usersList.add(data);
                contactsAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(contactsAdapter);
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
}
