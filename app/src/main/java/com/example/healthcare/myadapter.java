package com.example.healthcare;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillId;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class myadapter extends RecyclerView.Adapter<myadapter.myViewholder> {
    ArrayList<model> datalist;

    public myadapter(ArrayList<model> datalist) {
        this.datalist = datalist;
    }
    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_single,parent,false);
        return new myViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewholder holder, final int position) {
        holder.t.setText("Dr. "+datalist.get(position).getFullName());
        holder.i.setText(datalist.get(position).getUid());

       holder.t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.t.getContext(),details.class);
                intent.putExtra("uname","Dr. "+datalist.get(position).getFullName());
                intent.putExtra("uid",datalist.get(position).getUid());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.t.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myViewholder extends RecyclerView.ViewHolder{

        TextView t,i;
        public myViewholder(@NonNull View itemView) {
            super(itemView);
            t=itemView.findViewById(R.id.list_name);
            i=itemView.findViewById(R.id.list_id);
        }
    }
    }




