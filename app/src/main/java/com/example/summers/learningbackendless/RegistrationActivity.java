package com.example.summers.learningbackendless;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class RegistrationActivity extends AppCompatActivity {
    private Button registerButton;
private EditText usernameEnter, passwordEnter, passwordConfirm, firstNameEdit, lastNameEdit, emailEdit;
private BackendlessUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        usernameEnter = findViewById(R.id.usernameEnter);
        passwordEnter = findViewById(R.id.passwordEnter);
        passwordConfirm = findViewById(R.id.passwordConfirm);
        firstNameEdit = findViewById(R.id.firstNameEdit);
        lastNameEdit = findViewById(R.id.lastNameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        registerButton = findViewById(R.id.buttonRegister);


        Backendless.initApp(this, BackendSettings.APP_ID, BackendSettings.API_KEY);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(passwordEnter.getText().toString().equals(passwordConfirm.getText().toString())) {
                 user = new BackendlessUser();
                 user.setProperty("usernameEdit", usernameEnter.getText().toString());
                 user.setProperty("firstName", firstNameEdit.getText().toString());
                 user.setProperty("lastName", lastNameEdit.getText().toString());
                 user.setProperty("email", emailEdit.getText().toString());
                 user.setPassword(passwordEnter.getText().toString());
                 Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                     public void handleResponse(BackendlessUser registeredUser) {

                     }

                     public void handleFault(BackendlessFault fault) {
                         Toast.makeText(RegistrationActivity.this, fault.getMessage(),Toast.LENGTH_SHORT).show();
                     }
                 });
                 finish();
             }
            else{
                 Toast.makeText(RegistrationActivity.this,"Passwords do not match.",Toast.LENGTH_SHORT).show();
             }

        }

    });
    }
    }

