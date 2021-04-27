package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class signup extends AppCompatActivity {
    EditText fullname,email,password,mobilenumber;
    Button login,register;
    Boolean value=true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    CheckBox isDoctor,isPatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Intent intent = getIntent();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mobilenumber = findViewById(R.id.mobilenumber);
        login = findViewById(R.id.gotologin);
        register = findViewById(R.id.button4);

        isDoctor=findViewById(R.id.checkBox);
        isPatient=findViewById(R.id.checkBox2);

        //checkbox logic
        isDoctor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    isPatient.setChecked(false);
                }
            }
        });
        isPatient.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    isDoctor.setChecked(false);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Ckeckfield(fullname);
                Ckeckfield(email);
                Ckeckfield(password);
                Ckeckfield(mobilenumber);

                //checkbox validation
                if(!(isDoctor.isChecked()||isPatient.isChecked())){
                    Toast.makeText(signup.this, "Select the Account Type", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (value) {
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).
                            addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                    Toast.makeText(signup.this, "Account Created", Toast.LENGTH_SHORT).show();
                                    //To identify user and to get same uid.
                                    DocumentReference documentReference = firebaseFirestore.collection("Users")
                                            .document(firebaseUser.getUid());
                                    Map<String, Object> userinfo = new HashMap<>();
                                    userinfo.put("FullName", fullname.getText().toString());
                                    userinfo.put("UserEmail", email.getText().toString());
                                    userinfo.put("PhoneNumber", mobilenumber.getText().toString());
                                    userinfo.put("Uid", firebaseUser.getUid());
                                    //Specify if user is Doctor
                                    if(isDoctor.isChecked()){
                                        userinfo.put("Is Doctor","1");
                                        userinfo.put("Patients","None");
                                        userinfo.put("Problem Recieved","None");
                                        userinfo.put("Solution Sent","None");
                                    }
                                    if(isPatient.isChecked()){
                                        userinfo.put("Is Patient","1");
                                        userinfo.put("Doctor Consulted","None");
                                        userinfo.put("Problem Sent","None");
                                        userinfo.put("Solution Recieved","Sorry, Doctor Haven't Seen Yet.");
                                    }

                                    documentReference.set(userinfo);
                                    if(isDoctor.isChecked()) {
                                        startActivity(new Intent(getApplicationContext(),doctor.class));
                                        finish();
                                    }
                                    if(isPatient.isChecked()) {
                                        startActivity(new Intent(getApplicationContext(),patientna.class));
                                        finish();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(signup.this, "Failed to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

    }
    public boolean Ckeckfield(EditText editText){
        if(editText.getText().toString().isEmpty()){
            editText.setError("Error");
            value=false;
        }
        else {
            value = true;
        }
        return value;
    }

}

