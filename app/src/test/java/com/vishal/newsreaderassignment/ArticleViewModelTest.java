package com.vishal.newsreaderassignment;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.vishal.newsreaderassignment.model.Article;
import com.vishal.newsreaderassignment.repository.ArticleRepository;
import com.vishal.newsreaderassignment.viewmodel.ArticleViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ArticleViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private ArticleViewModel viewModel;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private Observer<List<Article>> observer;

    @Before
    public void setUp() {
        viewModel = new ArticleViewModel(articleRepository);
        viewModel.articleLiveData.observeForever(observer);
    }

    @Test
    public void testLoadArticles() {
        Context context = Mockito.mock(Context.class);
        List<Article> mockArticles = new ArrayList<>();
        mockArticles.add(new Article(new Date(), "Title1", "https://google.com", "https:google.com"));
        Mockito.when(articleRepository.loadJsonFromAssets(Mockito.anyString(), Mockito.any(Context.class)))
                .thenReturn("jsonString");
        Mockito.when(articleRepository.getArticleList(Mockito.anyString())).thenReturn(mockArticles);
        viewModel.loadArticles(context);
        Mockito.verify(observer).onChanged(mockArticles);
    }

    @Test
    public void testSearchArticles() {
        // Given
        List<Article> mockArticles = new ArrayList<>();
        Article article1 = new Article(new Date(), "Title1", "https://google.com", "https:google.com");
        Article article2 = new Article(new Date(), "Title1 Title 2", "https://google.com", "https:google.com");
        mockArticles.add(article1);
        mockArticles.add(article2);
        viewModel.articles = mockArticles;

        // Observe the LiveData
        Observer<List<Article>> observer = Mockito.mock(Observer.class);
        viewModel.articleLiveData.observeForever(observer);

        // When
        viewModel.searchArticles("Search");

        // Then
        Mockito.verify(observer).onChanged(Mockito.anyList());
    }
}