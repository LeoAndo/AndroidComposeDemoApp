package com.example.templateapp01.ui.favorite

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.templateapp01.MainActivity
import com.example.templateapp01.data.repository.UnsplashRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FavoriteScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    internal lateinit var unsplashRepository: UnsplashRepository

    @Before
    fun setUp() {
        composeTestRule.setContent {
            FavoriteScreen(viewModel = FavoriteViewModel())
        }
    }

    @Test
    fun screen_state_init() {
        composeTestRule.onNodeWithText("Fanctionality not available").assertIsDisplayed()
    }
}