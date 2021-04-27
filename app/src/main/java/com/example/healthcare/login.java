package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthcare.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {

    EditText email,password;
    Button login,register;
    Boolean value=true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent=getIntent();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        email=findViewById(R.id.emaill);
        password=findViewById(R.id.passwordl);
        login=findViewById(R.id.loginbtn);
        register=findViewById(R.id.registerbtn);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkfield(email);
                Checkfield(password);

                if (value){
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText()
                            .toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public boolean Checkfield(EditText editText) {
        if(editText.getText().toString().isEmpty()){
            editText.setError("Error");
            value=false;
        }
        else {
            value = true;
        }
        return value;
    }


    private void checkUserAccessLevel(String uid) {
        
        DocumentReference documentReference=firebaseFirestore.collection("Users").document(uid);
        //extract data frm document.
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+documentSnapshot.getData());
               // Identify user is doctor or patient.
                if(documentSnapshot.getString("Is Doctor")!=null){
                    startActivity(new Intent(getApplicationContext(), doctor.class));
                    finish();
                }
                if(documentSnapshot.getString("Is Patient")!=null){
                    startActivity(new Intent(getApplicationContext(),patientna.class));
                    finish();
                }
            }
        });
    }


    public  void register(View view){
        Intent intent=new Intent(this,signup.class);
        startActivity(intent);
    }

}
