package com.lastminute.hoteldemo.base.mvi

interface MVIViewModelContract<INTENT> {
    fun processIntent(intent: INTENT)
}