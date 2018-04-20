package com.trying.developing.taskarrangement.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.trying.developing.taskarrangement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;


    @BindView(R.id.emailLogin)
     EditText emailLogin;
    @BindView(R.id.passwordLogin)
     EditText passwordLogin;
    private String mEmail;
    private String mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        setTitle("Sign in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    public void login(View view) {
        mEmail = emailLogin.getText().toString();
        mPassword = passwordLogin.getText().toString();

        if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {

            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();

        } else {

            mAuth.signInWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(SignInActivity.this, AllTasksActivity.class);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(SignInActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }

                }
            });


        }


    }
}
