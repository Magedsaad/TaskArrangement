package com.trying.developing.taskarrangement.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.trying.developing.taskarrangement.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.MainActiviy));


    }

    public void createaccount(View view){
        Intent intent=new Intent(this,CreateAccountActivity.class);
        startActivity(intent);
    }
    public void signin(View view){

        Intent intent=new Intent(this,SignInActivity.class);
        startActivity(intent);

    }


}
