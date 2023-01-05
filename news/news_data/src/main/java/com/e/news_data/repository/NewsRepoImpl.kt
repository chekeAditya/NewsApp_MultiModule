package com.e.news_data.repository

import com.e.news_data.mapper.toDomainArticle
import com.e.news_data.network.NewsApiService
import com.e.news_domain.model.Article
import com.e.news_domain.repository.NewsRepository


class NewsRepoImpl(private val newsApiService: NewsApiService) : NewsRepository {

    override suspend fun getNewsArticle(): List<Article> {
        //this is how we convert the response of article into list of article
        return newsApiService.getNewsArticles("us").articles.map { it.toDomainArticle() }
    }
}