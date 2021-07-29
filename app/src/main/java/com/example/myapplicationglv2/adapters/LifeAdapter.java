package com.example.myapplicationglv2.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationglv2.R;
import com.example.myapplicationglv2.databinding.ItemLifeBinding;
import com.example.myapplicationglv2.models.Life;

import java.util.List;

public class LifeAdapter extends RecyclerView.Adapter<LifeAdapter.MyViewHolder> {

    private List<Life> lifesList;

    public LifeAdapter(List<Life> lifesList) {
        this.lifesList = lifesList;
    }

    @Override
    public LifeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(ItemLifeBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LifeAdapter.MyViewHolder holder, int position) {

        if (lifesList.get(position).getActive()){
            holder.itemLifeBinding.heart.setImageResource(R.drawable.ic_baseline_favorite_24);
        }else {
            holder.itemLifeBinding.heart.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }

    }

    @Override
    public int getItemCount() {
        return lifesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ItemLifeBinding itemLifeBinding;

        public MyViewHolder(ItemLifeBinding itemLifeBinding) {
            super(itemLifeBinding.getRoot());
            this.itemLifeBinding = itemLifeBinding;
        }
    }

    public void removeLife(){
        int lastLife = -1;
        for(int i = 0 ; i< this.lifesList.size();i++){
            if(this.lifesList.get(i).getActive()){
               lastLife = i;
            }
        }
        if(lastLife!=-1){
            this.lifesList.get(lastLife).setActive(false);
        }
        notifyDataSetChanged();
    }
}