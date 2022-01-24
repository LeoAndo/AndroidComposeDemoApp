package com.example.templateapp01.ui.search

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.templateapp01.*
import com.example.templateapp01.common.saveScreenshot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import com.example.templateapp01.R

// @RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SearchScreenTest {
    companion object {
        private const val SCREENSHOT_FILENAME_PREFIX = "search_screen_test"
    }

    private lateinit var resources: Resources

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent { MyAppContent() }
        resources = ApplicationProvider.getApplicationContext<Context>().resources
        // navigate to Search Screen.
        composeTestRule.onNodeWithText("Search").performClick()
    }

    @Test
    fun input_query_enabled_button() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_text_field)).performTextInput("dogs")
            onNodeWithText(resources.getString(R.string.go_to_result_screen)).assertIsEnabled()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun navigate_result_screen() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_text_field)).performTextInput("dogs")
            onNodeWithText(resources.getString(R.string.go_to_result_screen)).performClick()
            onNodeWithText("ResultScreen").assertIsDisplayed()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun empty_input_query_enabled_button() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_text_field)).performTextInput("")
            onNodeWithText(resources.getString(R.string.go_to_result_screen)).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }
}