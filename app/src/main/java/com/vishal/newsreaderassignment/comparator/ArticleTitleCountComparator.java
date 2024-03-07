package com.vishal.newsreaderassignment.comparator;

import com.vishal.newsreaderassignment.model.Article;

import java.util.Comparator;

public class ArticleTitleCountComparator implements Comparator<Article> {

    @Override
    public int compare(Article article, Article t1) {
        return t1.getMatchCount() - article.getMatchCount();
    }
}
