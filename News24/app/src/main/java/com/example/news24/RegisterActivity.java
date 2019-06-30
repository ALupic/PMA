package com.example.news24;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText passwordConfEditText;
    Button registerBtn;
    TextView loginTextView;


    FirebaseFirestore firestoreRootRaf;
    CollectionReference itemRef;
    ArrayList itemList;
    Map<String, Object> docData;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firestoreRootRaf = FirebaseFirestore.getInstance();
        itemRef = firestoreRootRaf.collection("registeruser");
        itemList = new ArrayList<>();

        readData(new FirestoreCallback() {
            @Override
            public void onCallback( ) {
                Log.d("RegistrationActivityTAG", "AJDE");

            }
        });

    }

    private void readData(final FirestoreCallback firestoreCallback){

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

                docData = new HashMap<>();

                docData.put("password", pwd);
                docData.put("notifications",0);
                docData.put("type", 0);

                itemRef.document(user)
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {

                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("RegistrationActivityTAG", "DocumentSnapshot successfully written!");
                                firestoreCallback.onCallback();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("RegistrationActivityTAG", "Error writing document");
                            }

                        });




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

    private interface FirestoreCallback{
        void onCallback( );


    }
}
