package com.example.news24;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddFragmentArticle extends AppCompatActivity {
    DatabaseHelper myDB;
    Button btnAdd,btnView;
    EditText editText;
    EditText editTextContent;
    Spinner editTextSpinner;
    EditText editTextLat;
    EditText editTextLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fragment_article);
        editText = (EditText) findViewById(R.id.editText);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        editTextLat = (EditText) findViewById(R.id.editTextLat);
        editTextLong = (EditText) findViewById(R.id.editTextLong);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        myDB = new DatabaseHelper(this);


        editTextSpinner = (Spinner) findViewById(R.id.editTextSpinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayList<Category> categories =  myDB.getCategories();
        ArrayList<String> catStr = new ArrayList<>();

        for(Category c : categories){
            catStr.add(c.getTitle());
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                catStr
        );

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        editTextSpinner.setAdapter(adapter2);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                String content = editTextContent.getText().toString();
                String category = editTextSpinner.getSelectedItem().toString();
                String lat = editTextLat.getText().toString();
                String longg = editTextLong.getText().toString();
                if(editTextLat.length() ==0){
                    lat ="0";
                }
                if(editTextLong.length() ==0){
                    longg ="0";
                }

                if(editText.length()!= 0 && editTextContent.length() !=0){
                    AddData(newEntry,content, category, lat, longg );
                    editText.setText("");
                }else{
                    Toast.makeText(AddFragmentArticle.this, "You must put something in the text field!", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void AddData(String newEntry, String content, String category,String lat,String longg) {

        boolean insertData = myDB.addArticle(newEntry,content, category, lat, longg);

        if(insertData==true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
            Intent registerIntent = new Intent(this, AdminActivity.class);
            startActivity(registerIntent);
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
            Intent registerIntent = new Intent(this, AdminActivity.class);
            startActivity(registerIntent);
        }
    }
}
