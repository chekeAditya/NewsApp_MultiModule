package com.e.news_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.e.common_utils.Navigator
import com.e.news_presentation.databinding.ActivityNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity) {
            Intent(activity, NewsActivity::class.java).also {
                activity.startActivity(it)
            }
        }
    }

    private var _binding: ActivityNewsBinding? = null
    private val binding: ActivityNewsBinding?
        get() = _binding

    private val newsViewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        initViews()
        setObservers()
    }

    private fun initViews() {
        binding?.rvArticles?.adapter = newsAdapter
    }

    private fun setObservers() {
        lifecycleScope.launchWhenCreated {
            newsViewModel.newsArticle.collectLatest { it ->
                Log.d("Aditya", "setObservers: $it")

                if (it.isLoading) {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                if (it.error.isNotBlank()) {
                    binding?.progressBar?.visibility = View.GONE
                    Toast.makeText(this@NewsActivity, it.error, Toast.LENGTH_SHORT).show()
                }
                it.data?.let {
                    binding?.progressBar?.visibility = View.GONE
                    newsAdapter.setData(it)
                }
            }
        }
    }
}

object GoToNewsActivity : Navigator {

    override fun navigate(activity: Activity) {
        NewsActivity.launchActivity(activity)
    }

}