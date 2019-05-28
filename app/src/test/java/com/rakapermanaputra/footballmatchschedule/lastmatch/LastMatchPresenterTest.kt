package com.rakapermanaputra.footballmatchschedule.lastmatch

import com.rakapermanaputra.footballmatchschedule.matches.lastmatches.LastMatchContract
import com.rakapermanaputra.footballmatchschedule.matches.lastmatches.LastMatchPresenter
import com.rakapermanaputra.footballmatchschedule.model.Event
import com.rakapermanaputra.footballmatchschedule.model.EventResponse
import com.rakapermanaputra.footballmatchschedule.model.repository.EventRepositoryImpl
import com.rakapermanaputra.footballmatchschedule.utils.SchedulerProvider
import com.rakapermanaputra.footballmatchschedule.utils.TestSchedulerProvider
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LastMatchPresenterTest {

    @Mock
    lateinit var mView: LastMatchContract.View

    @Mock
    lateinit var eventRepoImpl: EventRepositoryImpl

    lateinit var schedulerProvider: SchedulerProvider

    lateinit var presenter: LastMatchPresenter

    lateinit var eventResponse: EventResponse

    lateinit var response: Flowable<EventResponse>

    private val event = mutableListOf<Event>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TestSchedulerProvider()
        eventResponse = EventResponse(event)
        response = Flowable.just(eventResponse)
        presenter = LastMatchPresenter(mView, eventRepoImpl, schedulerProvider)
        `when`(eventRepoImpl.getLastMatch("4335")).thenReturn(response)
    }

    @Test
    fun getLastMatch() {
        presenter.getLastMatch("4335")
        verify(mView).showLoading()
        verify(mView).showScheduleList(event)
        verify(mView).hideLoading()
    }
}