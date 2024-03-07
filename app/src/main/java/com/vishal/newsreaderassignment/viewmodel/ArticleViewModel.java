package com.vishal.newsreaderassignment.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vishal.newsreaderassignment.compartor.ArticleDateComparator;
import com.vishal.newsreaderassignment.repository.ArticleRepository;
import com.vishal.newsreaderassignment.model.Article;

import java.util.List;

public class ArticleViewModel extends ViewModel {
    private final MutableLiveData<List<Article>> articleMutableLiveData = new MutableLiveData<>();
    private final ArticleRepository articleRepository;
    public LiveData<List<Article>> articleLiveData = articleMutableLiveData;

    public List<Article> articles;

    public ArticleViewModel(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void loadArticles(Context context) {
        String articleStringJson = articleRepository.loadJsonFromAssets("news.json", context);
        if (articleStringJson != null) {
            List<Article> articleList = articleRepository.getArticleList(articleStringJson);
            if (articleList != null) {
                articleList.sort(new ArticleDateComparator());
                articles = articleList;
                articleMutableLiveData.setValue(articles);
            }
        }
    }

    public void searchArticles(String value) {
        List<Article> matchedArticles = articleRepository.searchArticles(articles, value);
        articleMutableLiveData.setValue(matchedArticles);
    }
}
