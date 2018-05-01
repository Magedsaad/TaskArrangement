package com.trying.developing.taskarrangement.ui;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Chat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChattingActivity extends AppCompatActivity {

    @BindView(R.id.messageEditText)
    EditText messagesarea;
    String friend_id;
    private DatabaseReference mChatDatabase;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

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

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mChatDatabase = FirebaseDatabase.getInstance().getReference();

        Toast.makeText(getApplicationContext(),friend_id,Toast.LENGTH_SHORT).show();



    }


    public void send(View view){

        String mCurrent_userid=user.getUid();
        String mMessages=messagesarea.getText().toString();

        final Chat chats=new Chat(mMessages,friend_id);

        mChatDatabase.child("chat").child(mCurrent_userid).push().setValue(chats).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                mChatDatabase.child("chat").child(friend_id).push().setValue(chats);

                Toast.makeText(getApplicationContext(),friend_id,Toast.LENGTH_SHORT).show();

            }
        });





    }
}
