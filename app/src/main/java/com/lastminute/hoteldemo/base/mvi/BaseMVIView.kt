package com.lastminute.hoteldemo.base.mvi

interface BaseMVIView<STATE> {
    fun onCallBackState(state: STATE)
}