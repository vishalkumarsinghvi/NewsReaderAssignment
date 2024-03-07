package com.vishal.newsreaderassignment.viewmodelfactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vishal.newsreaderassignment.repository.ArticleRepository;

import java.lang.reflect.InvocationTargetException;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final ArticleRepository articleRepository;

    public ViewModelFactory(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(ArticleRepository.class).newInstance(articleRepository);
        } catch (IllegalAccessException | NoSuchMethodException | InstantiationException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
