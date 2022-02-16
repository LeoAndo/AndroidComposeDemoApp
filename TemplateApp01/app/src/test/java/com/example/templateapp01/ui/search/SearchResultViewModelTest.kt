package com.example.templateapp01.ui.search

import com.example.templateapp01.CoroutinesTestRule
import com.example.templateapp01.data.FailureResult
import com.example.templateapp01.domain.repository.UnsplashRepository
import com.example.templateapp01.domain.model.UnSplashPhoto
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import org.mockito.Spy

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class SearchResultViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Spy
    private lateinit var unsplashRepository: UnsplashRepository
    private lateinit var searchResultViewModel: SearchResultViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchResultViewModel = SearchResultViewModel(unsplashRepository)
    }

    @Test
    fun `successful search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue: Result<List<UnSplashPhoto>> = buildList {
                repeat(3) { id ->
                    add(
                        UnSplashPhoto(
                            id = id.toString(),
                            urls = UnSplashPhoto.UnSplashPhotoUrls(full = "", regular = ""),
                            likes = 10,
                            user = UnSplashPhoto.UnSplashUser(
                                username = "Taro Yamada",
                                profileImage = UnSplashPhoto.UnSplashUser.ProfileImage(small = null)
                            )
                        )
                    )
                }
            }.let {
                Result.success(it)
            }
            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            searchResultViewModel.searchPhotos(query = "dogs")
            assert(searchResultViewModel.uiState is SearchResultUiState.Photos)
        }
    }

    @Test
    fun `empty search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue: Result<List<UnSplashPhoto>> = Result.success(emptyList())
            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            searchResultViewModel.searchPhotos(query = "dogs")
            assert(searchResultViewModel.uiState is SearchResultUiState.NoPhotos)
        }
    }

    @Test
    fun `failure search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue = Result.failure<FailureResult>(FailureResult.Network("error!"))

            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            searchResultViewModel.searchPhotos(query = "dogs")
            assert(searchResultViewModel.uiState is SearchResultUiState.Failure)
        }
    }
}