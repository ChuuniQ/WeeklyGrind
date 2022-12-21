package com.example.weeklygrind;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText regfName, reglName, regEmail, regPass, confPass;
    private Button signUpBtn;
    private FirebaseAuth fireAuth;
    private FirebaseFirestore fireStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regfName = findViewById(R.id.userFName);
        reglName = findViewById(R.id.userLName);
        regEmail = findViewById(R.id.signEmail);
        regPass = findViewById(R.id.signPassword);
        confPass = findViewById(R.id.confPassword);
        signUpBtn = findViewById(R.id.signupBtn);

        fireAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = regEmail.getText().toString().trim();
                String password = regPass.getText().toString().trim();
                String confirmPass = confPass.getText().toString().trim();

                String firstname = regfName.getText().toString();
                String lastname = reglName.getText().toString();

                if (TextUtils.isEmpty(firstname)){
                    regfName.setError("First name is required.");
                    return;
                }

                if (TextUtils.isEmpty(lastname)){
                    reglName.setError("Last name is required.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    regEmail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    regPass.setError("Password is required.");
                    return;
                }

                if (password.length() < 8){
                    regPass.setError("Password must be longer than 8 characters.");
                    return;
                }

                if (TextUtils.isEmpty(confirmPass)){
                    confPass.setError("Please confirm password.");
                    return;
                }

                if (!confirmPass.equals(password)){
                    confPass.setError("Passwords do not match.");
                    return;
                }

                fireAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User successfully registered!", Toast.LENGTH_SHORT).show();
                            userID = fireAuth.getCurrentUser().getUid();
                            DocumentReference docRef = fireStore.collection("Users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("Email", email);
                            user.put("First Name", firstname);
                            user.put("Last Name", lastname);
                            docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG, "onSuccess: User profile created for " + userID);
                                }
                            });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}