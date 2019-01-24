package com.example.rateaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.List;
import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText usernameField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private EditText emailField;
    private Button buttonCreateAccount;
    private ConstraintLayout bgelement;

    private String name;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private List<String> accountInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Intent receivedIntent = getIntent();
        username = receivedIntent.getStringExtra(LoginActivity.EXTRA_USERNAME);
        usernameField.setText(username);

        wireWidgets();
        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameField.getText().toString();
                username = usernameField.getText().toString();
                password = passwordField.getText().toString();
                confirmPassword = confirmPasswordField.getText().toString();
                email = emailField.getText().toString();
                if(isValid(name, username, password, confirmPassword, email)){
                    registerAccountOnBackendless(name, username, password, email);
                }
            }
        });
    }

    private void registerAccountOnBackendless(String name, final String username, String password, String email) {
        BackendlessUser user = new BackendlessUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setProperty("username", username);
        user.setProperty("name", name);

        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Intent loginIntent = new Intent();
                loginIntent.putExtra(LoginActivity.EXTRA_USERNAME, username);
                setResult(RESULT_OK, loginIntent);
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }

    private boolean isValid(String name, String username, String password, String confirmPassword, String email) {
        if(!Objects.equals(name, "") && !Objects.equals(username, "") &&
                !Objects.equals(password, "") && !Objects.equals(email, "")){
            if(password.length()<6 || password.length()>12){
                if(Objects.equals(password, confirmPassword)){
                    return true;
                } else {
                    Toast.makeText(this, "Your password and confirm password fields do "+
                            "not match", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void wireWidgets() {
        nameField = findViewById(R.id.editText_createaccount_name);
        usernameField = findViewById(R.id.editText_createaccount_username);
        passwordField = findViewById(R.id.editText_createaccount_password);
        confirmPasswordField = findViewById(R.id.editText_createaccount_confirmpassword);
        emailField = findViewById(R.id.editText_createaccount_email);
        buttonCreateAccount = findViewById(R.id.button_createaccount_create);
        bgelement = findViewById(R.id.createaccountlayout);
    }
}
