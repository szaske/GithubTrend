/**
 * This is simply the data table to store the time the cache was created
 */
package com.loc8r.cache.model

import android.arch.persistence.room.Entity

@Entity
class Config(val lastCacheTime: Long) {
}