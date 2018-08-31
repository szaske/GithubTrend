package com.loc8r.mobile_ui.utils

import com.loc8r.domain.models.Project

object TestProjectFactory {

    fun makeProject(): Project {
        return Project(TestDataFactory.randomString(),TestDataFactory.randomString(),TestDataFactory.randomString(),
                TestDataFactory.randomString(),TestDataFactory.randomString(),TestDataFactory.randomString(),
                TestDataFactory.randomString(),TestDataFactory.randomBoolean())
    }
}