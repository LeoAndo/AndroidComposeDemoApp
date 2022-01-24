package com.example.templateapp01.ui.favorite

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.templateapp01.MainActivity
import com.example.templateapp01.MyAppContent
import com.example.templateapp01.common.saveScreenshot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
class FavoriteScreenTest {

    // TODO: WORK AROUND
    companion object {
        private const val ADD_BUTTON_TEXT = "Add Todo Item"
        private const val TAG_FIELD_TITLE = "tag_field_title"
        private const val TAG_FIELD_MEMO = "tag_field_memo"
        private const val TAG_TODO_ITEM_LIST = "tag_todo_item_list"
        private const val DELETE_BUTTON_TEXT = "Delete All Todo Items"
    }

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent { MyAppContent() }
        // navigate to FavoriteScreen.
        with(composeTestRule) {
            onNodeWithText("Favorite").performClick()
        }
    }

    @Test
    fun state_init_not_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithText(ADD_BUTTON_TEXT).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = "state_init_not_enabled_add_item_button",
                node = onRoot()
            )
        }
    }

    @Test
    fun input_todo_data_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithTag(TAG_FIELD_TITLE).performTextInput("hobby")
            onNodeWithTag(TAG_FIELD_MEMO).performTextInput("pokemon")
            onNodeWithText(ADD_BUTTON_TEXT).assertIsEnabled()
            saveScreenshot(
                fileNamePrefix = "input_todo_data_enabled_add_item_button",
                node = onRoot()
            )
        }
    }

    @Test
    fun empty_input_memo_not_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithTag(TAG_FIELD_TITLE).performTextInput("hobby")
            onNodeWithTag(TAG_FIELD_MEMO).performTextInput("")
            onNodeWithText(ADD_BUTTON_TEXT).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = "empty_input_memo_not_enabled_add_item_button",
                node = onRoot()
            )
        }
    }

    @Test
    fun empty_input_title_not_enabled_add_item_button() {
        with(composeTestRule) {
            onNodeWithTag(TAG_FIELD_TITLE).performTextInput("")
            onNodeWithTag(TAG_FIELD_MEMO).performTextInput("pokemon")
            onNodeWithText(ADD_BUTTON_TEXT).assertIsNotEnabled()
            saveScreenshot(
                fileNamePrefix = "empty_input_title_not_enabled_add_item_button",
                node = onRoot()
            )
        }
    }

    @Test
    fun add_todo_item_displayed_todo_item_list() {
        with(composeTestRule) {
            onNodeWithTag(TAG_FIELD_TITLE).performTextInput("hobby")
            onNodeWithTag(TAG_FIELD_MEMO).performTextInput("pokemon")
            onNodeWithText(ADD_BUTTON_TEXT).performClick()
            onNodeWithTag(TAG_TODO_ITEM_LIST).assertIsDisplayed()
            saveScreenshot(
                fileNamePrefix = "add_todo_item_displayed_todo_item_list",
                node = onRoot()
            )
        }
    }

    @Test
    fun delete_all_todo_items_not_displayed_todo_item_list() {
        with(composeTestRule) {
            onNodeWithText(DELETE_BUTTON_TEXT).performClick()
            onNodeWithTag(TAG_TODO_ITEM_LIST).assertIsNotDisplayed()
            saveScreenshot(
                fileNamePrefix = "delete_all_todo_items_not_displayed_todo_item_list",
                node = onRoot()
            )
        }
    }
}