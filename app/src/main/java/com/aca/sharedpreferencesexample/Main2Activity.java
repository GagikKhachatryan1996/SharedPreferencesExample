package com.aca.sharedpreferencesexample;


import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);

        if(menu instanceof MenuBuilder){

            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.ADD:
                Toast.makeText(getApplicationContext(),"Add Clicked",Toast.LENGTH_LONG).show();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                startActivity(intent);
                return true;

            default:

                super.onOptionsItemSelected(item);

        }
        return true;

    }
}


