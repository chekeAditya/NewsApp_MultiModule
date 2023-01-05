# MultiModule Application

Steps:

**Create a new Module**

1. Go to file -> New -> New Module -> Android Library -> Give Module Name (like:news)
2. Follows the same steps for search module

**Create sub module**

1. Right click on news -> new -> new Module -> Android Library -> give module Name like (:news:mylibrary)
2. Follows the same step for news_domain & news_presentation

**For Build.Gradle**

1. Shift the project module
2. Right click(RC) on NewsApp main module -> Directory -> (directory name is reserved here) so name it as buildSrc
3. RC on buildSrc create a file with specific name *build.gradle.kts*.
4. In this to enable kotlin dsl we have to write 2 line of code as shown below
5. RC on buildSrc crate new directory **src/main/java**
6. Crete new file Dependencies

```kotlin
//here you can add all the dependencies which needed in project
object Versions {
    const val core = "1.9.0"
    const val appcompat = "1.5.1"
    const val androidMaterial = "1.6.1"
    const val constraintLayout = "2.1.4"

    const val testImplJunit = "4.13.2"
    const val androidTestImplJunit = "1.1.3"
    const val androidTestEspresso = "3.4.0"

    const val retrofit = "2.9.0"
    const val gsonConvertor = "2.9.0"
    const val okHttp = "4.9.0"
    const val scalerConvertor = "2.1.0"

    const val kotlinCoroutines = "1.6.1"

    const val coroutineLifecycleScope = "2.5.1"

    const val glide = "4.12.0"

    const val viewModelDeligate = "1.6.0"

    const val dagger = "2.44"
    const val hiltCompiler = "1.0.0"

    const val roomVersion = "2.4.3"

    const val swipeRefresh = "1.1.0"

    const val lottieAnimations = "3.4.2"
}

object Deps {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
}

object TestImplementation {
    const val junit = "junit:junit:${Versions.testImplJunit}"
}

object AndroidTestImplementation {
    const val junit = "androidx.test.ext:junit:${Versions.androidTestImplJunit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.androidTestEspresso}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gsonConvertor = "com.squareup.retrofit2:converter-gson:${Versions.gsonConvertor}"
    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val scalersConvertors = "com.squareup.retrofit2:converter-scalars:${Versions.scalerConvertor}"
}

object Coroutines {
    const val coroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
    const val coroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
}

object CoroutinesLifecycleScope {
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.coroutineLifecycleScope}"
    const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.coroutineLifecycleScope}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val annotationProcessor = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object ViewModelDelegate {
    const val viewModelDeligate = "androidx.activity:activity-ktx:${Versions.viewModelDeligate}"
}

object DaggerHilt {
    const val hilt = "com.google.dagger:hilt-android:${Versions.dagger}"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltCompiler}"
}

object Room {
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val room = "androidx.room:room-ktx:${Versions.roomVersion}"
}

object CircularProgressBar {
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
}

object LottieAnimations {
    const val lottieAnimations = "com.airbnb.android:lottie:${Versions.lottieAnimations}"
}
```

1. Create 2 more module i.e News and Search
    1. News → Here create 3 java module
        1. news_data
        2. news_domain
        3. news_presentation
    2. Search → Here create 3 java module
        1. search_data
        2. search_domain
        3. search_presentation
2. Crate one more module i.e common :- here you’ll have that functions which is common and can be used in every module.
    1. Common → Here we will have only one java module.
        1. common_utils

---

Navigation between the module

- For making the connection between modules you have to implement project in `build.gradle`

```groovy
implementation project(":news:news_presentation")
implementation project(":search:search_presentation")
implementation project(":common:common_utils")
```

- Now in common module we have assign the activities which we have in the project or where we have to navigate

```kotlin
package com.e.common_utils

sealed class Activities{
    object NewsActivities:Activities()
    object SearchActivity:Activities()
}
```

- Created a navigator interface

```kotlin
package com.e.common_utils

import android.app.Activity

interface Navigator {
		//in this we have to pass the origin of the activity
    fun navigate(activity: Activity)

    interface Provider {
				//in this we have to pass the desination of the activity
        fun getActivities(activities: Activities): Navigator
    }
}
```

- We also have to create a DefaultNavigator

```kotlin
package com.e.newsapp.navigation

import com.e.common_utils.Activities
import com.e.common_utils.Navigator
import com.e.news_presentation.GoToNewsActivity
import com.e.search_presentation.GoToSearchActivity

class DefaultNavigator : Navigator.Provider {

    override fun getActivities(activities: Activities): Navigator {
        return when (activities) {
            Activities.NewsActivities -> {
                GoToNewsActivity
            }

            Activities.SearchActivity -> {
                GoToSearchActivity
            }
        }
    }
}
```

- Also have to create a MainModule

```kotlin
package com.e.newsapp

import com.e.common_utils.Navigator
import com.e.newsapp.navigation.DefaultNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Provides
    @Singleton
    fun provideProvider(): Navigator.Provider {
        return DefaultNavigator()
    }

}
```

- MainActivity

```kotlin
package com.e.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.e.common_utils.Activities
import com.e.common_utils.Navigator
import com.e.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var provider: Navigator.Provider

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding?
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
window.statusBarColor= ContextCompat.getColor(this, R.color.white)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        provider.getActivities(Activities.NewsActivities).navigate(this)
    }
}
```

Now here if you want to move from one activity to other then you have to inject provider so that we can access it’s method. Through which we can navigate from one activity to another.

- In the NewsActivity

```kotlin
package com.e.news_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.e.common_utils.Navigator

class NewsActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity) {
            Intent(activity, NewsActivity::class.java).also{
								activity.startActivity(it)
						}
				}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
    }
}

object GoToNewsActivity : Navigator {

    override fun navigate(activity: Activity) {
        NewsActivity.launchActivity(activity)
    }
}
```

### Title :- Setup Rest Api

Here we are will be using News Rest Api

Api :- https://newsapi.org/v2/topheadlinescountry=us&category=business&apiKey={api_key}

Here you’ll the response. Convert that response from Json to Pojo.

**news_data (Module) →** here we will need some package’s

1. Model → here we will store all the data classes, it’s name will be NewsDTO, ArticleDTO & ResponseDTO (as here we are appending the data)

    ```kotlin
    data class ResponseModel(
        val articles: List<ArticleDTO>,
        val status: String,
        val totalResults: Int
    )
    data class ArticleDTO(
        val author: String,
        val content: String,
        val description: String,
        val publishedAt: String,
        val source: SourceDTO,
        val title: String,
        val url: String,
        val urlToImage: String
    )
    data class SourceDTO(
        val id: String,
        val name: String
    )
    ```


Now we have to add dependencies of Dagger Hilt in news_data build.gradle file also add plugin’s

1. DI → create an object in DI package e.g NewsDataModule

    ```kotlin
    //Now if we have to provide any dependencies we can provide here
    @InstallIn(SingletonComponent::class)
    @Module
    object NewsDataModule {
    
    }
    ```


Now Go in news_domain module and create package’s. (this is domain layer model which we used in our application to show data in it’s presentation layer)

1. DI →  create an object in DI package e.g `NewsDomainModule` annotating which with module as  this annotation will tell dagger that this is also a module use this one too.

```kotlin
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object NewsDomainModule {
}
```

1. model → add those parameter’s which you’ll going to use in the presentation layer (app)

```kotlin
// This is the data class which we will use in our presenter class
class Article(
    val author: String,
    val content: String,
    val description: String,
    val title: String,
    val urlToImage: String
)
```

1. Repository
2. use_case

Add Dependencies of dagger Hilt as we did in news_data build.gradle

In network package create a interface NewsApiService

```kotlin
import com.e.common_utils.Constants
import com.e.news_data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNewsArticles(
        @Query("country") country: String = Constants.COUNTRY,
        @Query("category") category: String = Constants.CATEGORY,
        @Query("apiKey") apiKey: String = Constants.API_KEY,
    ): ResponseModel

}
```

- Create Constants in common_utils module to store all the constants present in application.
  We are storing them in different module as if we have to access that in any other module then we can do that in a very easy way.
- To access the constant or anything from another moduel then you have to specify that into it’s dependency i.e `implementation project(":common:common_utils")`
- Create a network package in common_module also add dependencies of hilt & retrofit in common_module so that we access hilt here.
- Create a function provideRepository which will return us the repository

```kotlin

import com.e.common_utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CommonModule {

    @Provides
    @Singleton
    fun provideRepository(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
//here we are not using any interceptor to make it simple
//from here we will get the base url and endpoint will get from NewsApiService
    }
}
```

- Now till set the repository of our `news_domain` module

```kotlin
import com.e.news_domain.model.Article

interface NewsRepository {
    suspend fun getNewsArticle(): List<Article>
}
```

- Create Repository in `news_data` module NewsRepoImpl i.e `NewsRepoImpl`and extend that with `NewsRepository`also implement the method’s.

```kotlin
import com.e.news_domain.model.Article
import com.e.news_domain.repository.NewsRepository

class NewsRepoImpl : NewsRepository {

    override suspend fun getNewsArticle(): List<Article> {
        return
    }

}
```

- Go To `NewsDataModule`create a function which will provideNewsApiService to `NewsRepoImpl`

```kotlin
import com.e.news_data.network.NewsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object NewsDataModule {
    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

}
```

- Now we can return the article by getting the response from the api

```kotlin
class NewsRepoImpl(private val newsApiService: NewsApiService) : NewsRepository{

    override suspend fun getNewsArticle(): List<Article> {
//here we are getting getting ResponseModel but we need List<Article> so let's create a Mapper class
        return newsApiService.getNewsArticles()
    }

}
```

- Function of Mapper class is to return ListOfArticle from ResponseModel

```kotlin
fun ArticleDTO.toDomainArticle(): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        title = this.title,
        urlToImage = this.urlToImage
    )
}
```

```kotlin
class NewsRepoImpl(private val newsApiService: NewsApiService) : NewsRepository {

    override suspend fun getNewsArticle(): List<Article> {
        //this is how we convert the response of article into list of article
        return newsApiService.getNewsArticles().articles.map{ it.toDomainArticle()
		}
	}
}
```

- New we have to add this function into `NewsDataModule` so that we can access it from anywhere by just injecting using dagger hilt

```kotlin
@Provides
fun provideNewsRepository(newsApiService: NewsApiService): NewsRepository {
    return  NewsRepoImpl(newsApiService)
}
```

Now All the thing are completed in `news_data` module

let me send all all the classes,object create in `news_data` module

- DI →

```kotlin
import com.e.news_data.network.NewsApiService
import com.e.news_data.repository.NewsRepoImpl
import com.e.news_domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object NewsDataModule {

    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    fun provideNewsRepository(newsApiService: NewsApiService): NewsRepository {
        return  NewsRepoImpl(newsApiService)
    }

}
```

- Mapper

```kotlin
import com.e.news_data.model.ArticleDTO
import com.e.news_domain.model.Article

fun ArticleDTO.toDomainArticle(): Article {
    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        title = this.title,
        urlToImage = this.urlToImage
    )
}
```

- NewsApiService

```kotlin
import com.e.common_utils.Constants
import com.e.news_data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNewsArticles(
        @Query("country") country: String = Constants.COUNTRY,
        @Query("category") category: String = Constants.CATEGORY,
        @Query("apiKey") apiKey: String = Constants.API_KEY,
    ): ResponseModel

}
```

- NewsRepoImpl

```kotlin
import com.e.news_data.mapper.toDomainArticle
import com.e.news_data.network.NewsApiService
import com.e.news_domain.model.Article
import com.e.news_domain.repository.NewsRepository

class NewsRepoImpl(private val newsApiService: NewsApiService) : NewsRepository {

    override suspend fun getNewsArticle(): List<Article> {
        //this is how we convert the response of article into list of article
        return newsApiService.getNewsArticles().articles.map{ it.toDomainArticle()}
}
}
```

- Also Model classes of DTO

Now we need a sealed class to maintain the state of the response like weather the it’s loading, success or throws an error

```kotlin
sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T?) : Resource<T>()
    class Error<T>(val message: String, val data: T? = null) : Resource<T>()
}
```

- Now

```kotlin
import com.e.common_utils.Resource
import com.e.news_domain.model.Article
import com.e.news_domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNewsArticleUseCase(private val newsRepository: NewsRepository) {

    operator fun invoke(): Flow<Resource<List<Article>>> =flow{
						emit(Resource.Loading())
		        try {
		            emit(Resource.Success(data = newsRepository.getNewsArticle()))
		        } catch (e: Exception) {
		            emit(Resource.Error(message = e.message.toString()))
		        }
		}
}
```

Now we can jump into our presentation layer but before that we have to provide this use_case in our dagger hilt (DI) as we will inject this into viewModel so dagger should know about this.

```kotlin
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
```

- Now Crate activity_news.xml based on your requirement i’ve kept it so simple

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:textSize="30sp"
    tools:context=".NewsActivity">

    <data>

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/header"
            android:layout_width="match_parent"
            android:layout_height="55dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent">

            <androidx.appcompat.widget.AppCompatTextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginStart="12dp"
                android:text="News Article"
                android:textColor="@color/black"
                android:textSize="24sp"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />

        </androidx.constraintlayout.widget.ConstraintLayout>

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/rv_articles"
            android:layout_width="match_parent"
            android:layout_height="0dp"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/header" />

    </androidx.constraintlayout.widget.ConstraintLayout>

</layout>
```

- Also Create an item_layout for recycleview

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>

    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tvHeadlines"
            style="@style/TextAppearance.MaterialComponents.Headline5"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="699dp"
            android:paddingHorizontal="8dp"
            android:paddingVertical="12dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            tools:text="Headline 5" />

        <ImageView
            android:id="@+id/ivArticle"
            android:layout_width="match_parent"
            android:layout_height="300dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/tvHeadlines" />

        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/tvContent"
            style="@style/TextAppearance.MaterialComponents.Body1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginBottom="699dp"
            android:paddingHorizontal="8dp"
            android:paddingVertical="12dp"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/ivArticle"
            tools:text="Headline 5" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

- New add dependencies of Dagger Hilt in newPresentationLayer also add kapt plugin in build.gradle of newsPresentation module, now here we can use `@AndroidEntryPoint` annotation in our news Activity

```kotlin
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.e.common_utils.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity) {
            Intent(activity, NewsActivity::class.java).also{
activity.startActivity(it)
}
}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

    }
}

object GoToNewsActivity : Navigator {

    override fun navigate(activity: Activity) {
        NewsActivity.launchActivity(activity)
    }

}
```

- Now we will create  `viewModel` now to Inject getNewsUseCase in the constructor but we can’t directly call that so we have to implement dependencies of `news:news_domain`

```kotlin
import androidx.lifecycle.ViewModel
import com.e.news_domain.use_case.GetNewsArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsArticleUseCase) : ViewModel() {

}
```

- Now for managing the state we will create a data class NewsState in news_presentation package

```kotlin
import com.e.news_domain.model.Article

data class NewsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<Article>? = null
)
```

- New we will create `NewsViewModel`

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e.common_utils.Resource
import com.e.news_domain.use_case.GetNewsArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsUseCase: GetNewsArticleUseCase) : ViewModel() {

    //For managing the state we are using MutableStateFlow
    private val _newsArticle =MutableStateFlow(NewsState())
    val newsArticle: StateFlow<NewsState> = _newsArticle

    init {
        getNewsArticles()
    }

    fun getNewsArticles() {
	      getNewsUseCase().onEach{
					when (it) {
                is Resource.Loading -> {
                    _newsArticle.value = NewsState(isLoading = true)
                }

                is Resource.Error -> {
                    _newsArticle.value = NewsState(error =it.message)
                }

                is Resource.Success -> {
                    _newsArticle.value = NewsState(data =it.data)
                }
            }
					}.launchIn(viewModelScope)
    }

}
```

- Now in the news Activity we’ll setup our data binding but before that enable data binding from build.gradle file of `news:presentaion` module