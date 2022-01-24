package com.example.templateapp01.ui.search

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.templateapp01.*
import com.example.templateapp01.common.saveScreenshot
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ResultScreenTest {
    // TODO: WORK AROUND
    companion object {
        private const val TAG_FIELD_QUERY = "tag_text_field"
        private const val BUTTON_TEXT = "to Result Screen."
        private const val RESULT_SCREEN_TEXT = "ResultScreen"
    }

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent { MyAppContent() }

        // navigate to ResultScreen.
        with(composeTestRule) {
            composeTestRule.onNodeWithText("Search").performClick()
            onNodeWithTag(TAG_FIELD_QUERY).performTextInput("dogs")
            onNodeWithText(BUTTON_TEXT).performClick()
        }
    }

    @Test
    fun result_screen_state_init() {
        with(composeTestRule) {
            composeTestRule.onNodeWithText(RESULT_SCREEN_TEXT).assertIsDisplayed()
            saveScreenshot(
                fileNamePrefix = "result_screen_state_init",
                node = onRoot()
            )
        }
    }
}