package com.example.news_tab.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.news_tab.App;
import com.example.news_tab.R;
import com.example.news_tab.databinding.FragmentHomeBinding;
import com.example.news_tab.databinding.FragmentNewsBinding;
import com.example.news_tab.models.News;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private News news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news = (News) requireArguments().getSerializable("task");
        if (news != null){
            binding.editText.setText(news.getTitle());
            Log.e("News", "title"+ news.getTitle());
            binding.btnSave.setText("Edit");
        }
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        Bundle bundle = new Bundle();
        String text = binding.editText.getText().toString();
        if (text.isEmpty()){
            Toast.makeText(requireContext(), "Title is empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (news == null){
            news = new News(text,System.currentTimeMillis());
            Toast.makeText(requireContext(), "News was successfully added", Toast.LENGTH_SHORT).show();
        }else{
            news.setTitle(text);
            Toast.makeText(requireContext(), "News was successfully updated", Toast.LENGTH_SHORT).show();
        }
        News news=new News(text,System.currentTimeMillis());
        App.getDatabase().newsDao().insert(news);
        bundle.putSerializable("text", news);
        getParentFragmentManager().setFragmentResult("rk_news",bundle);
        close();
    }
    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}