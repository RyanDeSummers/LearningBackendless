package com.example.summers.learningbackendless;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView passwordText, usernameText;
    private EditText password, usernameEdit;
    private Button accountButton, loginButton;
    private ProgressBar loadingBar;

    public static final int REG_REQUEST = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        passwordText = findViewById(R.id.passwordText);
        password = findViewById(R.id.passwordEdit);
        usernameText = findViewById(R.id.usernameText);
        usernameEdit = findViewById(R.id.usernameEdit);
        accountButton = findViewById(R.id.accountButton);
        loginButton = findViewById(R.id.loginButton);
        loadingBar = findViewById(R.id.progressBar);
        loadingBar.setVisibility(View.GONE);

        Backendless.initApp(this, BackendSettings.APP_ID, BackendSettings.API_KEY);

        accountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                //startActivity(i);
                startActivityForResult(i, REG_REQUEST);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                loadingBar.setVisibility(View.VISIBLE);
                Backendless.UserService.login(usernameEdit.getText().toString(), password.getText().toString(), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        String user = (String) response.getProperty("usernameEdit");
                        Toast.makeText(LoginActivity.this,user+" has logged on.",Toast.LENGTH_SHORT).show();
                        loadingBar.setVisibility(View.GONE);
                        //testDataRetrieval();
                        testDataManipulation();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(LoginActivity.this, fault.getMessage(),Toast.LENGTH_SHORT).show();
                        loadingBar.setVisibility(View.GONE);
                    }
                });
                CountDownTimer timer = new CountDownTimer(2000,1000) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish(){
                     loadingBar.setVisibility(View.GONE);
                    }
                }.start();
            }
        });
        }


    private void testDataRetrieval() {


        //String whereClause = "name = 'Gus''s BBQ'";

        String id = Backendless.UserService.CurrentUser().getUserId();
        String whereClause = "objectId = '" + id + "'";
        DataQueryBuilder dataQuery = DataQueryBuilder.create();
        dataQuery.setWhereClause(whereClause);
        Backendless.Data.of(Restaurant.class).find(dataQuery, new AsyncCallback<List<Restaurant>>() {
            @Override
            public void handleResponse(List<Restaurant> response) {
               Log.d("LOOK HERE:", "testDataRetrieval: " + response);
            }
            @Override
            public void handleFault(BackendlessFault fault) {
                    Toast.makeText(LoginActivity.this, fault.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
//        Backendless.Persistence.of(Restaurant.class).find(new AsyncCallback<List<Restaurant>>() {
//            @Override
//            public void handleResponse(List<Restaurant> response) {
//                Log.d("LOOK HERE:", "testDataRetrieval: " + response);
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//
//            }
//        });

//
   }

   private void testDataManipulation() {
        Restaurant r = new Restaurant("Food Barn", "Generic Good", "The Barn down the street", 5, 1);


        r.setObjectId("8B14C4D8-1FFF-BE92-FF70-D98D81569C00");
        r.setOwnerId(Backendless.UserService.CurrentUser().getUserId());
        r.setName("SOMETHING DIFFERENT");


        Backendless.Persistence.save(r, new AsyncCallback<Restaurant>() {
            @Override
            public void handleResponse(Restaurant response) {
                Log.d("CREATED NEW: ", "handleResponse: " + response);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(),Toast.LENGTH_SHORT).show();
            }


        });
//       Backendless.Persistence.of(Restaurant.class).remove(r, new AsyncCallback<Long>() {
//           @Override
//           public void handleResponse(Long response) {
//               Log.d("REMOVAL:", "handleResponse: " + response);
//           }
//
//           @Override
//           public void handleFault(BackendlessFault fault) {
//               Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
//           }
//       });
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REG_REQUEST);
        {
            //usernameEdit.setText(data.getStringExtra());
        }
    }
}




