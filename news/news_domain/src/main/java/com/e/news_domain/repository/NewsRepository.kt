package com.e.news_domain.repository

import com.e.news_domain.model.Article

interface NewsRepository {

    suspend fun getNewsArticle(): List<Article>
}