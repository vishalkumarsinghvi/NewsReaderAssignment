package com.vishal.newsreaderassignment.repository;

import android.content.Context;

import com.vishal.newsreaderassignment.model.Article;

import java.util.List;

public interface ArticleRepository {
    String loadJsonFromAssets(String fileName, Context context);

    List<Article> getArticleList(String jsonString);

    List<Article> searchArticles(List<Article> articles, String value);
}
