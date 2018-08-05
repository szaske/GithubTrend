package com.loc8r.presentation.interfaces

interface PresentationMapper<V, D> {

    // A function that accepts a D domain model and returns a V ProjectView data mode representation
    fun mapToView(type: D): V
}