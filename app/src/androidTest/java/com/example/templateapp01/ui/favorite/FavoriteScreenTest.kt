package com.example.templateapp01.ui.favorite

import android.content.Context
import android.content.res.Resources
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.templateapp01.MainActivity
import com.example.templateapp01.R
import com.example.templateapp01.MyAppContent
import com.example.templateapp01.common.saveScreenshot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

// @RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
class FavoriteScreenTest {
    companion object {
        private const val SCREENSHOT_FILENAME_PREFIX = "favorite_screen_test"
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
        // navigate to FavoriteScreen.
        with(composeTestRule) {
            onNodeWithText("Favorite").performClick()
        }
    }

    @Test
    fun state_init_not_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithText(resources.getString(R.string.add_todo_item)).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun input_todo_data_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_field_title)).performTextInput("hobby")
            onNodeWithTag(resources.getString(R.string.tag_field_memo)).performTextInput("pokemon")
            onNodeWithText(resources.getString(R.string.add_todo_item)).assertIsEnabled()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun empty_input_memo_not_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_field_title)).performTextInput("hobby")
            onNodeWithTag(resources.getString(R.string.tag_field_memo)).performTextInput("")
            onNodeWithText(resources.getString(R.string.add_todo_item)).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun empty_input_title_not_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_field_title)).performTextInput("")
            onNodeWithTag(resources.getString(R.string.tag_field_memo)).performTextInput("pokemon")
            onNodeWithText(resources.getString(R.string.add_todo_item)).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun add_todo_item_displayed_todo_item_list() {
        with(composeTestRule) {
            onNodeWithTag(resources.getString(R.string.tag_field_title)).performTextInput("hobby")
            onNodeWithTag(resources.getString(R.string.tag_field_memo)).performTextInput("pokemon")
            onNodeWithText(resources.getString(R.string.add_todo_item)).performClick()
            onNodeWithTag(resources.getString(R.string.tag_todo_item_list)).assertIsDisplayed()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }

    @Test
    fun delete_all_todo_items_not_displayed_todo_item_list() {
        with(composeTestRule) {
            onNodeWithText(resources.getString(R.string.delete_todo_items)).performClick()
            onNodeWithTag(resources.getString(R.string.tag_todo_item_list)).assertIsNotDisplayed()
            saveScreenshot(
                fileNamePrefix = SCREENSHOT_FILENAME_PREFIX,
                node = onRoot()
            )
        }
    }
}