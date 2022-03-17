package com.example.news_tab.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news_tab.databinding.ItemNewsBinding;
import com.example.news_tab.interfaces.OnClickListener;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_tab.R;
import com.example.news_tab.models.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.ViewHolder> {
    private Context context;
    private ArrayList<News> list;
    private OnClickListener onItemClickListener;

    public NewsAdaptor() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(list.indexOf(news));
    }

    public void setOnItemClickListener(OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public News getItem(int position) {
        return list.get(position);
    }

    public void insertItem(News news, int index) {
        list.set(index, news);
        notifyItemChanged(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;

        public ViewHolder(@NonNull ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onItemLongClick(getAdapterPosition());
                    new AlertDialog.Builder(view.getContext()).setTitle("Delete")
                            .setMessage("Are you sure?").setNegativeButton("NO", null)
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(view.getContext(), "DELETE", Toast.LENGTH_SHORT).show();
                                    list.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                            }).show();
                    return true;
                }
            });

        }

        public void bind(News news) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss, dd MMM yyyy", Locale.ROOT);
            String data = String.valueOf(simpleDateFormat.format(news.getCreatedAt()));
            binding.smallText.setText(data);
            binding.textTitle.setText(news.getTitle());
            if (getAdapterPosition() % 2 == 0){
                binding.itemNews.setBackgroundColor(ContextCompat.getColor(context, R.color.Gray));
            }else {
                binding.itemNews.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
        }

    }
}
