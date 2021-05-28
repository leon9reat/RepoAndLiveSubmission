package com.medialink.repoandlivesubmission.ui.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.medialink.repoandlivesubmission.data.source.local.entity.Detail
import com.medialink.repoandlivesubmission.data.source.remote.repository.IRepository
import com.medialink.repoandlivesubmission.utils.Resource
import com.medialink.repoandlivesubmission.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FragmentViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IRepository

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<List<Detail>>>

    @Before
    fun setUp() {
    }

    @Test
    fun testResponOK() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Detail>())
                .`when`(repository)
                .getList(1)

            val viewModel = FragmentViewModel(repository)
            viewModel.getList().observeForever(apiUsersObserver)
            Mockito.verify(repository).getList(1)
            Mockito.verify(apiUsersObserver).onChanged(Resource.success(emptyList()))
            viewModel.getList().removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun testResponError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(repository)
                .getList(0)
            val viewModel = FragmentViewModel(repository)

            viewModel.fetchListData(0)
            viewModel.getList().observeForever(apiUsersObserver)
            Mockito.verify(repository).getList(0)
            Mockito.verify(apiUsersObserver).onChanged(
                Resource.error(
                    null,
                    errorMessage,
                )
            )
            viewModel.getList().removeObserver(apiUsersObserver)
        }
    }
}