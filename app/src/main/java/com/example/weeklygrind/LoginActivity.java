package com.example.weeklygrind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inEmail, inPassword;
    private Button logBtn;
    private FirebaseAuth fireAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inEmail = findViewById(R.id.userEmail);
        inPassword = findViewById(R.id.userPassword);
        logBtn = findViewById(R.id.loginBtn);
        fireAuth = FirebaseAuth.getInstance();

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inEmail.getText().toString().trim();
                String password = inPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    inEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    inPassword.setError("Password is required.");
                    return;
                }

                fireAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "User logged in.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });



    }

    public void toSignup (View v){

        Intent sign = new Intent(this,RegisterActivity.class);
        startActivity(sign);
    }

}