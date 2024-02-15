package com.example.ikepseuprojectone.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ikepseuprojectone.R;
import com.example.ikepseuprojectone.models.Medication;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    ArrayList<Medication> medicationArrayList;
    Context context;
    View view1;
    ViewHolder viewHolder1;

    public MyAdapter(Context context, ArrayList<Medication> values) {
        this.context = context;
        this.medicationArrayList = values;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewPatName;
        public TextView textViewMedName;
        public TextView textViewMedType;
        public TextView textViewDate;
        public TextView textViewTime;

        public ViewHolder(View v) {
            super(v);

            textViewPatName = v.findViewById(R.id.textViewRecyclerPatName);
            textViewMedName = v.findViewById(R.id.textViewRecyclerMedName);
            textViewMedType = v.findViewById(R.id.textViewRecyclerMedType);
            textViewDate = v.findViewById(R.id.textViewRecyclerDate);
            textViewTime = v.findViewById(R.id.textViewRecyclerTime);

        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view1 = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false);
        viewHolder1 = new ViewHolder(view1);

        // Set size of items
        viewHolder1.itemView.getLayoutParams().height = 512;

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewPatName.setText(medicationArrayList.get(position).getsPatName_());
        holder.textViewMedName.setText(medicationArrayList.get(position).getsMedName_());
        holder.textViewMedType.setText(medicationArrayList.get(position).getsMedType_());
        holder.textViewDate.setText(medicationArrayList.get(position).getsDate_());
        holder.textViewTime.setText(medicationArrayList.get(position).getsTime_());

        // OnClick listener
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, medicationArrayList.get(holder.getAdapterPosition()).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicationArrayList.size();
    }
}
