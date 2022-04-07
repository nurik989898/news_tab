package com.example.news_tab.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.news_tab.databinding.FragmentDashboardBinding;
import com.example.news_tab.models.News;
import com.example.news_tab.ui.home.NewsAdaptor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private NewsAdaptor adaptor;
    private ArrayList<News> arrayList;
    private FragmentDashboardBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
db.collection("news")
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        arrayList = new ArrayList<>();
        if (task.isSuccessful()){
            for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                Log.d("tag", documentSnapshot.getId() + "=> " + documentSnapshot.getData());
                News newss = documentSnapshot.toObject(News.class);
                arrayList.add(newss);
            }
            setAdapter();
        }else {
            Log.w("tag","Error getting documents.", task.getException());
        }
    }
});
    }

    private void setAdapter() {
        adaptor = new NewsAdaptor();
        binding.dashboard.setAdapter(adaptor);
        adaptor.addList(arrayList);
    }
}