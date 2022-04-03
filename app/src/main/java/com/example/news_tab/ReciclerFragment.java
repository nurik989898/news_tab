package com.example.news_tab;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.news_tab.databinding.FragmentReciclerBinding;

import java.util.ArrayList;


public class ReciclerFragment extends Fragment {
private FragmentReciclerBinding binding;
private ProfileAdaptor adaptor;
private ArrayList<Model> model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReciclerBinding.inflate(LayoutInflater.from(requireContext()),container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        adaptor = new ProfileAdaptor(model);
        binding.recyclerJacob.setAdapter(adaptor);
    }

    private void loadData() {
        model = new ArrayList<>();
        model.add(new Model("https://s3.amazonaws.com/the-drive-staging/message-editor%2F1528475104876-1-bmwi8.jpg"));
        model.add(new Model("https://s3.amazonaws.com/the-drive-staging/message-editor%2F1528475104876-1-bmwi8.jpg"));
        model.add(new Model("https://s3.amazonaws.com/the-drive-staging/message-editor%2F1528475104876-1-bmwi8.jpg"));
        model.add(new Model("https://s3.amazonaws.com/the-drive-staging/message-editor%2F1528475104876-1-bmwi8.jpg"));
        model.add(new Model("https://s3.amazonaws.com/the-drive-staging/message-editor%2F1528475104876-1-bmwi8.jpg"));
        model.add(new Model("https://s3.amazonaws.com/the-drive-staging/message-editor%2F1528475104876-1-bmwi8.jpg"));
    }
}