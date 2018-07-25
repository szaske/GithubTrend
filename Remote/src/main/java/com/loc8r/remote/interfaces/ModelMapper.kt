/**
 * In the Remote module we've created models that are specific to the
 * Remote layer. The Data module/layer has no reference to these models,
 * but will be using implementations of the Remote layer to retrieve data,
 * therefore we need to map these models to models that are compatible with
 * the data module/layer.  For this we'll implement a Mapper class.  This
 * is the interface for the Mapper class. By using an interface we're
 * enforcing consistency in case we want to create more mapper classes
 * in the future.
 *
 * see: https://medium.com/@elye.project/in-and-out-type-variant-of-kotlin-587e4fa2944c
 */
package com.loc8r.remote.interfaces

interface ModelMapper<in M, out E> {
    fun mapFromModel(model: M): E
}