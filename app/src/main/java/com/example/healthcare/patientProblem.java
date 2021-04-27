package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class patientProblem extends AppCompatActivity {
    EditText editText;
    Button button;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem);
        Intent intent=getIntent();

        editText=findViewById(R.id.patientproblem);
        button=findViewById(R.id.submit);
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(patientProblem.this, "Please Enter Your Problem.", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("Users").document(firebaseAuth.getUid()).
                            update("Problem Sent", editText.getText().toString());
                    Toast.makeText(patientProblem.this, "Your Problem Sent Successfully.", Toast.LENGTH_SHORT).show();

                    CollectionReference collectionReference = db.collection("Users");

                    Query query = collectionReference.whereEqualTo("FullName", getIntent().getStringExtra("name").substring(4));
                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                Log.d("Doctor ID", queryDocumentSnapshot.getId());


                                db.collection("Users").document(queryDocumentSnapshot.getId())
                                        .update("Problem Recieved", editText.getText().toString());

                            }
                        }
                    });
                    finish();
                }
            }
        });
    }
}
