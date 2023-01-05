package com.e.news_domain.model

// This is the data class which we will use in our presenter class
data class Article(
    val author: String,
    val content: String,
    val description: String,
    val title: String,
    val urlToImage: String
)