package com.example.templateapp01.ui.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.templateapp01.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class HomeScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent { MyAppContent() }
    }

    @Test
    fun state_init() {
        with(composeTestRule) {
            onNodeWithText("Home").assertIsDisplayed()
        }
    }
}