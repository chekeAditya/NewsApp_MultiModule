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