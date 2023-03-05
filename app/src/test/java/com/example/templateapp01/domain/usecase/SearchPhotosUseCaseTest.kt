package com.example.templateapp01.domain.usecase

import com.example.templateapp01.CoroutinesTestRule
import com.example.templateapp01.data.ErrorResult
import com.example.templateapp01.data.SafeResult
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
class SearchPhotosUseCaseTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Spy
    private lateinit var unsplashRepository: UnsplashRepository
    private lateinit var searchPhotosUseCase: SearchPhotosUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchPhotosUseCase = SearchPhotosUseCase(
            unsplashRepository = unsplashRepository,
            dispatcher = coroutinesTestRule.testDispatcher
        )
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
            when (searchPhotosUseCase(query = "dogs")) {
                is SafeResult.Error -> assert(false)
                is SafeResult.Success -> assert(true)
            }
        }
    }

    @Test
    fun `empty search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue: SafeResult<List<UnSplashPhoto>> = SafeResult.Success(emptyList())
            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            searchPhotosUseCase(query = "dogs")
            when (val ret = searchPhotosUseCase(query = "dogs")) {
                is SafeResult.Error -> assert(false)
                is SafeResult.Success -> {
                    assert(ret.data.isEmpty())
                }
            }
        }
    }

    @Test
    fun `failure search Photos`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {

            val retValue = SafeResult.Error(ErrorResult.NetworkError("error!"))

            doReturn(retValue).whenever(unsplashRepository).searchPhotos(query = "dogs")

            searchPhotosUseCase(query = "dogs")
            when (searchPhotosUseCase(query = "dogs")) {
                is SafeResult.Error -> assert(true)
                is SafeResult.Success -> assert(false)
            }
        }
    }
}