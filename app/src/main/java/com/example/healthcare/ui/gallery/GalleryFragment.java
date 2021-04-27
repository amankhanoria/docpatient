package com.example.healthcare.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.healthcare.R;
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

public class GalleryFragment extends Fragment {
    TextView name,email,problem,doctor,solution;
    DocumentReference dr;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        name=(TextView)view.findViewById(R.id.textView16);
        email=(TextView)view.findViewById(R.id.textView17);
        problem=(TextView)view.findViewById(R.id.textView18);
        doctor=(TextView)view.findViewById(R.id.textView19);
        solution=(TextView)view.findViewById(R.id.textView20);

        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        final String current=firebaseAuth.getUid();
        CollectionReference collectionReference = db.collection("Users");

        Query query=collectionReference.whereEqualTo("Uid",current);
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    Log.d("Doctor ID", queryDocumentSnapshot.getId());
                    name.setText(queryDocumentSnapshot.getString("FullName"));
                    email.setText(queryDocumentSnapshot.getString("UserEmail"));
                    problem.setText(queryDocumentSnapshot.getString("Problem Sent"));
                    doctor.setText("Dr. "+queryDocumentSnapshot.getString("Doctor Consulted"));
                    solution.setText(queryDocumentSnapshot.getString("Solution Recieved"));
                }
            }
        });



        return view;
    }
}
