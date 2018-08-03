package com.loc8r.cache.utils

import com.loc8r.cache.model.Config

object ConfigFactory {
    fun makeCachedConfig(): Config {
        return Config(DataFactory.randomInt(), DataFactory.randomLong())
    }
}