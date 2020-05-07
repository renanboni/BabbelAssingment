package com.example.babbelassignment.domain

import io.reactivex.Scheduler

interface SchedulerProvider {
    val mainThread: Scheduler
    val io: Scheduler
    val newThread: Scheduler
}