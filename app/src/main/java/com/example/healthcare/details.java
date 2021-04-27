package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class details extends AppCompatActivity {
TextView textView,namep;
    TextView did;
Button button;
FirebaseFirestore db;
DocumentReference dr;
FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textView=findViewById(R.id.doctor_name);
        did=findViewById(R.id.doc_id);
        namep=findViewById(R.id.name);
        button=findViewById(R.id.askdoctor);
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();


        textView.setText(getIntent().getStringExtra("uname").toString());
        did.setText(getIntent().getStringExtra("uid").toString());
         final String nameofpatient;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name=getIntent().getStringExtra("uname").toString();

               dr=db.collection("Users").document(firebaseAuth.getUid());
               dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(final DocumentSnapshot documentSnapshot) {
                       if(documentSnapshot.exists()){



                           db.collection("Users").document(firebaseAuth.getUid()).
                                   update("Doctor Consulted",getIntent().getStringExtra("uname").substring(4).toString());

                           CollectionReference collectionReference=db.collection("Users");

                           Query query=collectionReference.whereEqualTo("FullName",getIntent().getStringExtra("uname").substring(4));
                           query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                               @Override
                               public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                               List<DocumentSnapshot> snapshots=queryDocumentSnapshots.getDocuments();
                                   for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                                        Log.d("Doctor ID", queryDocumentSnapshot.getId());

                                       namep.setText(queryDocumentSnapshot.getId().toString());
                                       db.collection("Users").document(queryDocumentSnapshot.getId())
                                              .update("Patients",documentSnapshot.getString("FullName"));
                                       String Documentid = queryDocumentSnapshot.getId();
                                       namep.setText(Documentid);

                                   }
                               }
                           });



                           Toast.makeText(details.this, "You Successufully Consulted.", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
                Intent intent=new Intent(getApplicationContext(),patientProblem.class);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

}
