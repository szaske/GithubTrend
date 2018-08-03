/**
 * This is the generics interface for a mapper, which determins that we need 2 functions
 */
package com.loc8r.cache

interface CacheMapper<C, E> {
    fun mapFromCache(type: C): E
    fun mapToCache(type: E): C
}