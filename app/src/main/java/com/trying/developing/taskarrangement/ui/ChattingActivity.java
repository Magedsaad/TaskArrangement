package com.trying.developing.taskarrangement.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.adapter.MessagesAdapter;
import com.trying.developing.taskarrangement.model.Chat;
import com.trying.developing.taskarrangement.model.Tasks;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChattingActivity extends AppCompatActivity {

    @BindView(R.id.messageEditText)
    EditText messagesarea;
    String friend_id;
    private DatabaseReference mChatDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseDatabase FDB;
    private List<Chat>chatList;
    private MessagesAdapter messagesAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        ButterKnife.bind(this);


        Bundle bundle=getIntent().getExtras();
        friend_id =bundle.getString("friend_id");
        String email=bundle.getString("friend_email");

        setTitle(email);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView=(RecyclerView) findViewById(R.id.chattingRecycler);
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        FDB=FirebaseDatabase.getInstance();

        chatList=new ArrayList<>();
        messagesAdapter=new MessagesAdapter(getApplicationContext(),chatList);

        //Toast.makeText(getApplicationContext(),friend_id,Toast.LENGTH_SHORT).show();

        showMessages();

    }


    public void send(View view){

        final String mCurrent_userid=user.getUid();
        final String mMessages=messagesarea.getText().toString();

        final Chat chats=new Chat(mMessages,friend_id);
        mChatDatabase = FirebaseDatabase.getInstance().getReference();
        mChatDatabase.child("chat").child(mCurrent_userid).child(friend_id).push().setValue(chats).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mChatDatabase.child("chat").child(friend_id).child(mCurrent_userid).push().setValue(chats);

              //  Toast.makeText(getApplicationContext(),friend_id,Toast.LENGTH_SHORT).show();
                messagesarea.setText("");
            }
        });
    }

    public void showMessages(){

        FirebaseUser currentUser = mAuth.getCurrentUser();
        final String currentid=currentUser.getUid();
        mChatDatabase=FDB.getReference("chat").child(currentid).child(friend_id);
        mChatDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Chat data=dataSnapshot.getValue(Chat.class);
                chatList.add(data);
                //Toast.makeText(getApplicationContext(),data.getMessage(),Toast.LENGTH_SHORT).show();
                messagesAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(messagesAdapter);
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
