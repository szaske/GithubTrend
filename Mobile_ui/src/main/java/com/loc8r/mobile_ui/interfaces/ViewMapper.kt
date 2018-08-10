package com.loc8r.mobile_ui.interfaces

interface ViewMapper<in P,out V> {
    fun mapToView(presentation: P): V
}