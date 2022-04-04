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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.news_tab.App;
import com.example.news_tab.R;
import com.example.news_tab.databinding.FragmentHomeBinding;
import com.example.news_tab.databinding.FragmentNewsBinding;
import com.example.news_tab.models.News;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewsFragment extends Fragment {
    private FirebaseFirestore db;
    private FragmentNewsBinding binding;
    private News news;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

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
            binding.editTextFire.setText(news.getTitle());
            Log.e("News", "title"+ news.getTitle());
            binding.btnSaveFire.setText("Edit");
        }
        binding.btnSaveFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Shake)
                        .duration(700)
                        .repeat(5)
                        .playOn(binding.editTextFire);
                save();
            }
        });
    }

    private void save() {
        Bundle bundle = new Bundle();
        String text = binding.editTextFire.getText().toString();
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
        addDataToFirestore(news);
        bundle.putSerializable("text", news);
        getParentFragmentManager().setFragmentResult("rk_news",bundle);
    }

    private void addDataToFirestore(News news) {
        db.collection("news")
                .add(news)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                        close();
                        Log.d("tag", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show();

                        Log.w("tag", "Error adding document", e);
                    }
                });
    }
    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}