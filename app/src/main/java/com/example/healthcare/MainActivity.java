package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View;

import com.example.healthcare.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] dots;

    private Button next;
    private Button prev;

    private int Currentpage;

    private SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=(ViewPager)findViewById(R.id.slideviewpager);
        linearLayout=(LinearLayout)findViewById(R.id.linearlayout);

        next=(Button)findViewById(R.id.button2);
        prev=(Button)findViewById(R.id.button);

        sliderAdapter =new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);
        addDots(0);
        viewPager.addOnPageChangeListener(viewListener);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(Currentpage+1);
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(Currentpage-1);
            }
        });


    }

    public void addDots( int position){
        dots=new TextView[2];
        linearLayout.removeAllViews();
        for( int i=0;i< dots.length;i++){
                dots[i]= new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dots[i].setTextColor(getResources().getColor(R.color.transparentwhite));
                linearLayout.addView(dots[i]);
    }
        if(dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.kaala));
        }


 }

 ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
     @Override
     public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {

     }

     @Override
     public void onPageSelected(int i) {
      addDots(i);
      Currentpage=i;


      if(i==0){
          next.setEnabled(true);
          prev.setEnabled(false);
          prev.setVisibility(View.INVISIBLE);

          next.setText("NEXT");
          prev.setText("");
      }else if (i==dots.length-1) {
          next.setEnabled(true);
          prev.setEnabled(true);
          prev.setVisibility(View.VISIBLE);
          next.setVisibility(View.VISIBLE);

          next.setText("Finish");
          prev.setText("BACK");

          if(next.getText().equals("Finish"))
          { Finish();}
      }
      else {
          next.setEnabled(true);
          prev.setEnabled(true);
          prev.setVisibility(View.VISIBLE);
          next.setVisibility(View.VISIBLE);
          next.setText("NEXT");
          prev.setText("BACK");
      }
     }

     @Override
     public void onPageScrollStateChanged(int state) {

     }
 };


    public void Finish() {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,login.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                final DocumentReference documentReference= FirebaseFirestore.getInstance()
                        .collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                      if(documentSnapshot.getString("Is Doctor")!=null){
                        //if(documentSnapshot.getString("User is")=="Doctor"){
                            startActivity(new Intent(getApplicationContext(), doctor.class));
                            finish();
                        }
                       if(documentSnapshot.getString("Is Patient")!=null){
                      //  if(documentSnapshot.getString("User is")=="Patient"){
                            startActivity(new Intent(getApplicationContext(),patientna.class));
                            finish();
                        }
                    }
                }) .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(),login.class));
                        finish();
                    }
                });

            }
        }
    }
}
