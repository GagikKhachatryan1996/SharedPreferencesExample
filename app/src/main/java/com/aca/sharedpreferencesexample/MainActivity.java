package com.aca.sharedpreferencesexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity  {


    private Button btn_registration;
    private Button btn_login;
    private EditText editTextUsername,editTextPassword;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    private static final String SHARED_PREF="loginPrefs";

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        btn_registration = findViewById(R.id.btn_registration);
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        saveLoginCheckBox = findViewById(R.id.save);
        btn_login=findViewById(R.id.btn_login);


        mAuth = FirebaseAuth.getInstance();



        SharedPreferences loginPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            editTextUsername.setText(loginPreferences.getString("username", ""));
            editTextPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);

              String  username = editTextUsername.getText().toString();
              String  password = editTextPassword.getText().toString();

                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }


                if (username.isEmpty()) {
                    editTextUsername.setError("Email is required");
                    editTextUsername.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    editTextUsername.setError("Please enter a valid email");
                    editTextUsername.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError("Minimum lenght of password should be 6");
                    editTextPassword.requestFocus();
                    return;
                }






                createUser(username,password);






            }


        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  username = editTextUsername.getText().toString();
                String  password = editTextPassword.getText().toString();


                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextUsername.getWindowToken(), 0);


                if (saveLoginCheckBox.isChecked()) {
                    loginPrefsEditor.putBoolean("saveLogin", true);
                    loginPrefsEditor.putString("username", username);
                    loginPrefsEditor.putString("password", password);
                    loginPrefsEditor.commit();
                } else {
                    loginPrefsEditor.clear();
                    loginPrefsEditor.commit();
                }



                if (username.isEmpty()) {
                    editTextUsername.setError("Email is required");
                    editTextUsername.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
                    editTextUsername.setError("Please enter a valid email");
                    editTextUsername.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    editTextPassword.setError("Minimum lenght of password should be 6");
                    editTextPassword.requestFocus();
                    return;
                }


                loginUser(username,password);





            }
        });


    }

    public void createUser(String username, String password){





        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();


                            Toast.makeText(MainActivity.this, "Registration Succes", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    public void loginUser(String username, String password){





        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                            startActivity(intent);
                            MainActivity.this.finish();

                            Toast.makeText(MainActivity.this, "Login Succes", Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();

                        }


                    }
                });


    }
    public void doSomethingElse() {
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
        MainActivity.this.finish();
    }



}

