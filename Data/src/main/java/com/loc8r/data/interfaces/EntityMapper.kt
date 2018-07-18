/**
 * Now because we use 2 different model representatives, one in the Data module and one
 * in the Domain module, we need to create this mapper interface
 */

package com.loc8r.data.interfaces

interface EntityMapper<E, D> {

    // A function that accepts an E entity model and returns a D domain model representation
    fun mapFromEntity(entity: E): D

    // A function that accepts a D domain model and returns a E data mode representation
    fun mapToEntity(domain: D): E
}