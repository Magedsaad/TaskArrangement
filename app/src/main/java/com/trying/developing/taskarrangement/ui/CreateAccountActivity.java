package com.trying.developing.taskarrangement.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.trying.developing.taskarrangement.R;
import com.trying.developing.taskarrangement.model.Users;

public class CreateAccountActivity extends AppCompatActivity {

    private TextView userEmail;
    private TextView userPassword;
    private TextView userAge;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();
        userEmail=(TextView) findViewById(R.id.EmailId);
        userPassword=(TextView) findViewById(R.id.PasswordId);
        userAge=(TextView) findViewById(R.id.AgeId);
        radioGroup=(RadioGroup) findViewById(R.id.radioGroup);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void signup(View view){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);

        final String emailAddress=userEmail.getText().toString();
        final String emailPassword=userPassword.getText().toString();
        final String emailAge=userAge.getText().toString();
        final String positionlevel=radioButton.getText().toString();

        mAuth.createUserWithEmailAndPassword(emailAddress,emailPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    writeNewUser(user.getUid(), emailAddress, emailPassword,positionlevel, emailAge);

                    Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CreateAccountActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

    private void writeNewUser(String userId,String email,String password,String position,String age) {
        Users user = new Users(email,password,position,age);

        mDatabase.child("users").child(userId).setValue(user);
    }
}
