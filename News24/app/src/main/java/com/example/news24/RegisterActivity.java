package com.example.news24;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText passwordConfEditText;
    Button registerBtn;
    TextView loginTextView;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordConfEditText = findViewById(R.id.passwordConfEditText);
        registerBtn = findViewById(R.id.registerBtn);
        loginTextView = findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               String user = usernameEditText.getText().toString().trim();
               String pwd = passwordEditText.getText().toString().trim();
               String cnf_pwd = passwordConfEditText.getText().toString().trim();

               if(pwd.equals(cnf_pwd)){
                   long val = db.addUser(user, pwd);
                   if(val > 0){
                       Toast.makeText(RegisterActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                       Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                       startActivity(loginIntent);
                   }else{
                       Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(RegisterActivity.this, "Registration Error", Toast.LENGTH_SHORT).show();
               }
           }
        });
    }
}
