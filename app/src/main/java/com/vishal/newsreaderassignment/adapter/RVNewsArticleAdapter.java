package com.vishal.newsreaderassignment.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vishal.newsreaderassignment.R;
import com.vishal.newsreaderassignment.databinding.ItemRowArticleBinding;
import com.vishal.newsreaderassignment.model.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RVNewsArticleAdapter extends RecyclerView.Adapter<RVNewsArticleAdapter.NewsArticleViewHolder> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
    private List<Article> articleList;

    public RVNewsArticleAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowArticleBinding binding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticleViewHolder holder, int position) {
        Article current = articleList.get(position);
        holder.bind(current);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void setData(List<Article> articles) {
        this.articleList = articles;
        notifyDataSetChanged();
    }

    private String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public class NewsArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemRowArticleBinding binding;

        public NewsArticleViewHolder(ItemRowArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article article) {
            binding.tvArticleTitle.setText(article.getTitle());
            binding.tvArticlePublishAt.setText(formatDate(article.getPublishedAt()));
            Glide.with(binding.getRoot())
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.ic_place_holder)
                    .error(R.drawable.ic_place_holder)
                    .into(binding.ivArticle);
        }
    }
}