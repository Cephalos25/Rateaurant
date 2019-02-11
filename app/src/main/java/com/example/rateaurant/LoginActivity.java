package com.example.rateaurant;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    public static final String EXTRA_USERNAME = "d1n7e8/G";
    static final int USERNAME_REQUESTCODE = 12847;

    private EditText fieldUsername;
    private EditText fieldPassword;
    private Button buttonLogin;
    private TextView textViewCreateAccount;
    private ConstraintLayout bgelement;

    private String username;
    private String password;
    private Boolean loginSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        Backendless.initApp(this, Credentials.APP_ID, Credentials.API_KEY);

        setListeners();
    }

    private void setListeners() {
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempusername = fieldUsername.getText().toString();
                Intent createAccountIntent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                createAccountIntent.putExtra(EXTRA_USERNAME, tempusername);
                startActivityForResult(createAccountIntent, USERNAME_REQUESTCODE);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = fieldUsername.getText().toString();
                password = fieldPassword.getText().toString();
                loginToBackendless(username, password);
            }
        });
    }

    private void wireWidgets() {
        fieldUsername = findViewById(R.id.editText_login_username);
        fieldPassword = findViewById(R.id.editText_login_password);
        buttonLogin = findViewById(R.id.button_login_login);
        textViewCreateAccount = findViewById(R.id.textView_login_createaccount);
    }

    private void loginToBackendless(String username, String password) {
        Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                // Start new Activity here
                // occurs on login success
                fieldUsername.setText("");
                fieldPassword.setText("");
                Intent intent = new Intent(LoginActivity.this, RestaurantListActivity.class);
                startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == USERNAME_REQUESTCODE){
            if(resultCode == RESULT_OK){
                username = data.getStringExtra(EXTRA_USERNAME);
                fieldUsername.setText(username);
            }
        }
    }
}
