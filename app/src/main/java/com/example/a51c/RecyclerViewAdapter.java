package com.example.a51c;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<News> newsList;
    private Context context;
    private OnNewsClickListener  listener;

    private int layoutId;

    public interface OnNewsClickListener {
        void onNewsClick(News news);
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        News news = newsList.get(position);

        holder.tvNewsTitle.setText(newsList.get(position).getTitle());
        holder.imgNews.setImageURI(Uri.parse(newsList.get(position).getImageUri()));
        if (holder.description != null) {
            holder.description.setText(news.getDescription());
        }        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNewsClick(news);
            }
        });
    }

    public RecyclerViewAdapter(List<News> newsList, Context context, OnNewsClickListener listener, int layoutId) {
        this.newsList = newsList;
        this.context = context;
        this.listener = listener;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNews;
        TextView tvNewsTitle;
        TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNewsTitle = itemView.findViewById(R.id.tvNewsTitle);
            imgNews = itemView.findViewById(R.id.imgNews);
            description = itemView.findViewById(R.id.tvNewsDescription);
        }
    }
}
