package com.example.healthcare;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class myadapterdocter extends RecyclerView.Adapter<myadapterdocter.holder> {
    ArrayList<modelpatient> datalist;

    public myadapterdocter(ArrayList<modelpatient> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patientslist,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final holder holder, final int position) {
        holder.p.setText(datalist.get(position).getPatients());
        holder.p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.p.getContext(),doctorActivity.class);
                intent.putExtra("nname",datalist.get(position).getPatients());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.p.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }


    class holder extends RecyclerView.ViewHolder{
        TextView p;

        public holder(@NonNull View itemView) {

            super(itemView);
            p=itemView.findViewById(R.id.listofpatients);
        }
    }
}
