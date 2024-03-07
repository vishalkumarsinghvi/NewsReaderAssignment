package com.vishal.newsreaderassignment.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.vishal.newsreaderassignment.repository.ArticleRepositoryImpl;
import com.vishal.newsreaderassignment.viewmodel.ArticleViewModel;
import com.vishal.newsreaderassignment.adapter.RVNewsArticleAdapter;
import com.vishal.newsreaderassignment.viewmodelfactory.ViewModelFactory;
import com.vishal.newsreaderassignment.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RVNewsArticleAdapter rvNewsArticleAdapter;
    private ArticleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this, new ViewModelFactory(new ArticleRepositoryImpl())).get(ArticleViewModel.class);
        initNewsData();
        initRecyclerView();
        initSearchView();
        observeViewModel();
    }

    private void initNewsData() {
        viewModel.loadArticles(this);
    }

    private void initRecyclerView() {
        rvNewsArticleAdapter = new RVNewsArticleAdapter(new ArrayList<>());
        binding.rvNewsArticles.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNewsArticles.setAdapter(rvNewsArticleAdapter);
    }

    private void initSearchView() {
        binding.searchArticles.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.searchArticles(newText);
                return false;
            }
        });
    }

    private void observeViewModel() {
        viewModel.articleLiveData.observe(this, articles -> {
            rvNewsArticleAdapter.setData(articles);
        });
    }
}