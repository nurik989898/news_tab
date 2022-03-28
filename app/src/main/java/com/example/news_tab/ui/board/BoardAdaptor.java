package com.example.news_tab.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.news_tab.R;
import com.example.news_tab.databinding.FragmentBoardBinding;
import com.example.news_tab.databinding.FragmentHomeBinding;
import com.example.news_tab.databinding.ItemPagerBoadrBinding;
import com.example.news_tab.interfaces.OnClickListener;
import com.example.news_tab.models.Board;

import java.util.ArrayList;

public class BoardAdaptor extends RecyclerView.Adapter<BoardAdaptor.ViewHolder> {
    private ArrayList<Board> list;
    private OnClickListener onClickListener;

    public BoardAdaptor(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        list = new ArrayList<>();
        list.add(new Board("Salam", "Kyrghyz OPEN", R.drawable.ic_assignment));
        list.add(new Board("Hello", "English OPEN", R.drawable.ic_add));
        list.add(new Board("Annyuong", "Korean OPEN", R.drawable.ic_notifications_black_24dp));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemPagerBoadrBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPagerBoadrBinding binding;

        public ViewHolder(@NonNull ItemPagerBoadrBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position) {
            Board board = list.get(position);
            binding.textTitle.setText(board.getTitle());
            binding.textView.setText(board.getDesc());
            binding.imageView.setImageResource(board.getImage());
            if (position == list.size() - 1) {
                binding.buttonStart.setVisibility(View.VISIBLE);
            } else {
                binding.buttonStart.setVisibility(View.INVISIBLE);
            }
            binding.buttonStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick();
                }
            });

        }
    }
}
