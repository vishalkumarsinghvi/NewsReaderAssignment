package com.vishal.newsreaderassignment.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.vishal.newsreaderassignment.comparator.ArticleTitleCountComparator;
import com.vishal.newsreaderassignment.model.Article;
import com.vishal.newsreaderassignment.model.News;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepository {
    @Override
    public String loadJsonFromAssets(String fileName, Context context) {
        try (InputStream inputStream = context.getAssets().open(fileName)) {
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Article> getArticleList(String jsonString) {
        try {
            Gson gson = new Gson();
            News news = gson.fromJson(jsonString, News.class);
            return news.getArticleList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Article> searchArticles(List<Article> articles, String value) {
        List<Article> matchedArticles = new ArrayList<>();
        if (value.isEmpty()) {
            matchedArticles.addAll(articles);
        } else {
            for (Article article : articles) {
                int matchCount = countMatches(article.getTitle(), value);
                if (matchCount > 0) {
                    article.setMatchCount(matchCount);
                    matchedArticles.add(article);
                }
            }
            matchedArticles.sort(new ArticleTitleCountComparator());
        }
        return matchedArticles;
    }

    private int countMatches(String text, String searchString) {
        String lowercaseText = text.toLowerCase();
        String lowercaseSearchString = searchString.toLowerCase();

        int count = 0;
        int index = lowercaseText.indexOf(lowercaseSearchString);
        while (index != -1) {
            count++;
            index = lowercaseText.indexOf(lowercaseSearchString, index + 1);
        }

        return count;
    }
}
