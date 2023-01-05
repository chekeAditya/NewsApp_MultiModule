package com.e.search_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.e.common_utils.Navigator

class SearchActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity) {
            Intent(activity, SearchActivity::class.java).also {
                activity.startActivity(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}

object GoToSearchActivity : Navigator {

    override fun navigate(activity: Activity) {
        SearchActivity.launchActivity(activity)
    }

}