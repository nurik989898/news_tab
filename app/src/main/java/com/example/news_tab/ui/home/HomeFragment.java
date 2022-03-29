package com.example.news_tab.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.news_tab.App;
import com.example.news_tab.R;
import com.example.news_tab.databinding.FragmentHomeBinding;
import com.example.news_tab.interfaces.OnClickListener;
import com.example.news_tab.models.News;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private NewsAdaptor adaptor;
    private boolean isediting = false;
    private int index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaptor = new NewsAdaptor();
        List<News> list = App.getDatabase().newsDao().getAll();
        adaptor.addList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
       return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(null);
                isediting = false;
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk_news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                News news = (News) result.getSerializable("text");
                if (isediting){
                    adaptor.insertItem(news, index);
                }else{
                    adaptor.addItem(news);
                    Log.e("Home","text =" + news.getTitle());
                }

            }
        });
        binding.recyclerView.setAdapter(adaptor);
        adaptor.setOnItemClickListener(new OnClickListener() {
            @Override
            public void onItemClick(int position) {
            News news = adaptor.getItem(position);
                Toast.makeText(requireContext(), news.getTitle(), Toast.LENGTH_SHORT).show();
                isediting =true;
                open(news);
                index = position;
            }


            @Override
            public void onItemLongClick(int position) {

            }

            @Override
            public void itemClick() {

            }
        });
    }



    private void open(News news) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", news);
        navController.navigate(R.id.newsFragment,bundle);

    }
}