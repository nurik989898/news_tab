package com.example.news_tab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.news_tab.databinding.ItemProfileBinding;

import java.util.ArrayList;

public class ProfileAdaptor extends RecyclerView.Adapter<ProfileAdaptor.ViewHolder> {
    private ItemProfileBinding binding;
    ArrayList<Model> nol = new ArrayList<>();

    public ProfileAdaptor(ArrayList<Model> model) {
        this.nol = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemProfileBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.bind(nol.get(position));
    }

    @Override
    public int getItemCount() {
        return nol.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemProfileBinding binding;

        public ViewHolder(@NonNull ItemProfileBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Model model) {
            Glide.with(binding.image).load(model.getPhotos()).into(binding.image);
        }
    }
}
