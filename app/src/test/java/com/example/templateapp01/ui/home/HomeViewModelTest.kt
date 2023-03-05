package com.example.templateapp01.ui.home

import com.example.templateapp01.CoroutinesTestRule
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.data.SafeResult
import com.example.templateapp01.domain.repository.UnsplashRepository
import com.example.templateapp01.domain.model.UnSplashPhoto
import com.nhaarman.mockitokotlin2.*
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
class HomeViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Spy
    private lateinit var unsplashRepository: UnsplashRepository
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(unsplashRepository)
    }

    @Test
    fun `successful search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue: SafeResult<List<UnSplashPhoto>> = buildList {
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
                SafeResult.Success(it)
            }
            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            homeViewModel.searchPhotos()
            assert(homeViewModel.uiState is HomeUiState.Photos)
        }
    }

    @Test
    fun `empty search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue: SafeResult<List<UnSplashPhoto>> = SafeResult.Success(emptyList())
            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            homeViewModel.searchPhotos()
            assert(homeViewModel.uiState is HomeUiState.NoPhotos)
        }
    }

    @Test
    fun `failure search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue = SafeResult.Error(ErrorResult.NetworkError("error!"))

            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            homeViewModel.searchPhotos()
            assert(homeViewModel.uiState is HomeUiState.Error)
        }
    }
}