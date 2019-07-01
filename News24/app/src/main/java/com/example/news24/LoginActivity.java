package com.example.news24;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    Button loginBtn;
    TextView registerTextView;

    DatabaseHelper db;

    FirebaseFirestore firestoreRootRaf;
    CollectionReference itemRef;
    ArrayList<RegisteredUser> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);


        firestoreRootRaf = FirebaseFirestore.getInstance();
        itemRef = firestoreRootRaf.collection("registeruser");
        itemList = new ArrayList<>();
        readData(db, new FirestoreCallback() {
            @Override
            public void onCallback(List<RegisteredUser> list) {

            }
        });


    }

    private void readData(DatabaseHelper db, final FirestoreCallback firestoreCallback){

        itemRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot documentSnapshot : task.getResult()){
                        String username = documentSnapshot.getId();
                        String password = documentSnapshot.getString("password");
                        int type = documentSnapshot.getLong("type").intValue();
                        int notification = documentSnapshot.getLong("notifications").intValue();

                        RegisteredUser user = new RegisteredUser(username, password, type, notification);

                        itemList.add(user);
                    }
                    firestoreCallback.onCallback(itemList);

                    Log.d("LoginActivityTAG", itemList.toString());

                    usernameEditText = findViewById(R.id.usernameEditText);
                    passwordEditText = findViewById(R.id.passwordEditText);
                    loginBtn = findViewById(R.id.loginBtn);
                    registerTextView = findViewById(R.id.registerTextView);
                    registerTextView.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                            startActivity(registerIntent);
                        }
                    });

                    loginBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
                            String user = usernameEditText.getText().toString().trim();
                            String pwd = passwordEditText.getText().toString().trim();
                            int type = 0;
                            //Boolean res = db.checkUser(user, pwd);
                            Boolean res = false;

                            Log.d("LoginActivityTAG", itemList.toString());

                            for(RegisteredUser u : itemList){
                                if(u.getUsername().equals(user) && u.getPassword().equals(pwd)){
                                    Log.d("LoginActivityTAG", "Complete!");
                                    res = true;
                                    type = u.getType();
                                    break;
                                }else{
                                    Log.d("LoginActivityTAG", "Wrong!");
                                    res = false;
                                }
                            }

                            if(res == true){
                                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);

                                //SET USER SESSION
                                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();

                                editor.putString("username", user); //SET SESSION FOR USER
                                editor.putInt("type",type);
                                editor.commit();

                                //db.close();
                                startActivity(mainIntent);
                            }
                            else{
                                //db.close();
                                Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                } else {
                    Log.d("LoginActivityTAG", "Error getting document: ", task.getException());
                }
            }
        });


    }

    private interface FirestoreCallback{
        void onCallback(List<RegisteredUser> list);
    }

}
