package com.nemerald.fieldmapper;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.FieldViewHolder> {

    public static class FieldViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView fieldName;
        ImageView fieldPhoto;

        public FieldViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.item);
            fieldName = itemView.findViewById(R.id.field_name);
            fieldPhoto = itemView.findViewById(R.id.field_photo);
        }
    }
    List<Field> fields;

    RVAdapter(List<Field> fields){
        this.fields = fields;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public FieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        FieldViewHolder fvh = new FieldViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(FieldViewHolder fieldViewHolder, int position) {
        fieldViewHolder.fieldName.setText(fields.get(position).fieldName);
        fieldViewHolder.fieldPhoto.setImageResource(fields.get(position).fieldPhotoId);
    }

    @Override
    public int getItemCount() {
        return fields.size();
    }
}
