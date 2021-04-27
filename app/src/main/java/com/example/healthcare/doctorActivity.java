package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class doctorActivity extends AppCompatActivity {
    EditText editText;
    TextView  txtview8;
    Button button;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    DocumentReference dr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor2);

        Intent intent=getIntent();

        editText=findViewById(R.id.Solution);
        txtview8=findViewById(R.id.textView8);
        button=findViewById(R.id.doctorsolutionbutton);
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

     dr=db.collection("Users").document(firebaseAuth.getUid());
     dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
         @Override
         public void onSuccess(DocumentSnapshot documentSnapshot) {
             if (documentSnapshot.exists()){
                 txtview8.setText("Problem is: "+documentSnapshot.getString("Problem Recieved"));
             }
         }
     });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(doctorActivity.this, "Please Enter the Solution To the Problem.", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("Users").document(firebaseAuth.getUid()).
                            update("Solution Sent", editText.getText().toString());
                    Toast.makeText(doctorActivity.this, "Solution Sent Successfully.", Toast.LENGTH_SHORT).show();


                    CollectionReference collectionReference = db.collection("Users");

                    Query query = collectionReference.whereEqualTo("FullName", getIntent().getStringExtra("nname"));
                    query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                            for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                Log.d("Doctor ID", queryDocumentSnapshot.getId());


                                db.collection("Users").document(queryDocumentSnapshot.getId())
                                        .update("Solution Recieved", editText.getText().toString());

                            }
                        }
                    });
                    finish();
                    }
            }

        });

    }

    }