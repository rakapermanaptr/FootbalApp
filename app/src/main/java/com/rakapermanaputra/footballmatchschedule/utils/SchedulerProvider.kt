package com.rakapermanaputra.footballmatchschedule.utils

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun computation(): Scheduler
    fun trampoline(): Scheduler
    fun newTread(): Scheduler
    fun io(): Scheduler
}