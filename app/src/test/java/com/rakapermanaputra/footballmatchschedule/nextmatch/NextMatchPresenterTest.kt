package com.rakapermanaputra.footballmatchschedule.nextmatch

import com.rakapermanaputra.footballmatchschedule.matches.nextmatches.NextMatchContract
import com.rakapermanaputra.footballmatchschedule.matches.nextmatches.NextMatchPresenter
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

class NextMatchPresenterTest {

    @Mock
    lateinit var mView: NextMatchContract.View

    @Mock
    lateinit var eventRepositoryImpl: EventRepositoryImpl

    lateinit var schedulerProvider: SchedulerProvider

    lateinit var presenter: NextMatchPresenter

    lateinit var eventResponse: EventResponse

    lateinit var response: Flowable<EventResponse>

    private var event = mutableListOf<Event>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TestSchedulerProvider()
        eventResponse = EventResponse(event)
        response = Flowable.just(eventResponse)
        presenter = NextMatchPresenter(mView, eventRepositoryImpl, schedulerProvider)
        `when`(eventRepositoryImpl.getNextMatch("4335")).thenReturn(response)
    }

    @Test
    fun getNextMatch() {
        presenter.getNextMatch("4335")
        verify(mView).showLoading()
        verify(mView).showScheduleList(event)
        verify(mView).hideLoading()
    }
}