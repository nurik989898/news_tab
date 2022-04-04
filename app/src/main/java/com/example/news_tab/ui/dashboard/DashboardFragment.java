package com.example.news_tab.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.news_tab.databinding.FragmentDashboardBinding;
import com.example.news_tab.models.News;
import com.example.news_tab.ui.home.NewsAdaptor;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private NewsAdaptor adaptor;
    private ArrayList<News> arrayList;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData();
    }

    private void getData() {
        
    }

    private void setAdapter() {
        adaptor = new NewsAdaptor();
        binding.dashboard.setAdapter(adaptor);
        adaptor.addList(arrayList);
    }
}