package com.e.news_domain.di

import com.e.news_domain.repository.NewsRepository
import com.e.news_domain.use_case.GetNewsArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NewsDomainModule {

    //This NewsRepository we will get from the NewsDataModule presents in news_data module now we can inject this into viewModel
    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository): GetNewsArticleUseCase {
        return GetNewsArticleUseCase(newsRepository)
    }
}