package com.vishal.newsreaderassignment.model;

import com.google.gson.annotations.SerializedName;
import com.vishal.newsreaderassignment.model.Article;

import java.util.List;

public class News {
    @SerializedName("articles")
    private List<Article> articleList;

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
