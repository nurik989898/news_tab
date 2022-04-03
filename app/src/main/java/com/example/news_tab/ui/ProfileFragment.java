package com.example.news_tab.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.news_tab.MainActivity;
import com.example.news_tab.Model;
import com.example.news_tab.Prefs;
import com.example.news_tab.R;
import com.example.news_tab.ReciclerFragment;
import com.example.news_tab.databinding.FragmentNewsBinding;
import com.example.news_tab.databinding.FragmentProfileBinding;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class ProfileFragment extends Fragment {
    private ViewPagerAdaptor viewPagerAdaptor;
    private FragmentProfileBinding binding;
    private Uri uri;
    ArrayList<Model> model = new ArrayList<>();
    ArrayList<Fragment>viewPagerFragment = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setHasOptionsMenu(true);
        return binding.getRoot();


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.men, menu);
        menu.removeItem(R.id.clean);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cleanOne) {
            MainActivity.prefs.clearPreferences();
            binding.editText.setText(MainActivity.prefs.getText());
            if (MainActivity.prefs.getPic() != null) {
                uri = Uri.parse(MainActivity.prefs.getPic());
                Glide.with(binding.image).load(uri).circleCrop().into(binding.image);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity.prefs = new Prefs(requireContext());
        binding.image.setOnClickListener(view1 -> nuki());
        binding.editText.setText(MainActivity.prefs.getText());
        if (MainActivity.prefs.getPic() != null) {
            Glide.with(binding.image).load(MainActivity.prefs.getPic()).circleCrop().into(binding.image);

        }
        saveText();
        initViewPager();
    }

    private void initViewPager() {
        viewPagerAdaptor = new ViewPagerAdaptor(getChildFragmentManager(),0);
        viewPagerFragment.add(new ReciclerFragment());
        viewPagerFragment.add(new ReciclerFragment());
        viewPagerAdaptor.setFragment(viewPagerFragment);
        binding.viewPagerone.setAdapter(viewPagerAdaptor);
        binding.tabLo.setupWithViewPager(binding.viewPagerone);
        binding.tabLo.getTabAt(0).setIcon(R.drawable.dehaze);
        binding.tabLo.getTabAt(1).setIcon(R.drawable.dashboard_24);
    }

    private void nuki() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mGetContent.launch(intent);

    }

    private void saveText() {
        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                MainActivity.prefs.saveEditText(editable.toString());
            }
        });
    }


    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        uri = intent.getData();
                        Glide.with(binding.image).load(uri).circleCrop().into(binding.image);
                        MainActivity.prefs.savePicture(String.valueOf(uri));
                    }
                }
            });

    @Override
    public void onStart() {
        super.onStart();
        if (MainActivity.prefs.getPic() != null) {
            uri = Uri.parse(MainActivity.prefs.getPic());
            Glide.with(binding.image).load(uri).circleCrop().into(binding.image);
        }
    }
    class ViewPagerAdaptor extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragment = new ArrayList<>();

        public ViewPagerAdaptor(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragment.get(position);
        }

        @Override
        public int getCount() {
            return fragment.size();
        }

        public void setFragment(ArrayList<Fragment> fragment) {
            this.fragment = fragment;
        }
    }
}
