package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class doctor extends AppCompatActivity {

    ArrayList<modelpatient> datalist;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    myadapterdocter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Intent intent=getIntent();

        Button logoutd=findViewById(R.id.logoutdoctor);
        logoutd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });
        recyclerView = findViewById(R.id.doctorfirestorelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datalist=new ArrayList<modelpatient>();
        adapter=new myadapterdocter(datalist);
        recyclerView.setAdapter(adapter);
        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        final String current=firebaseAuth.getUid();
        String arr[];
        db.collection("Users").whereEqualTo("Uid",current).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list=queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot d:list){
                    modelpatient obj=d.toObject(modelpatient.class);
                    datalist.add(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

}
