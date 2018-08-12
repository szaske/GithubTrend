package com.loc8r.mobile_ui.utils

import com.loc8r.presentation.models.ProjectView

object ProjectFactory {

    fun makeProjectView(): ProjectView {
        return ProjectView(DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomString(),DataFactory.randomString(),
                DataFactory.randomString(),DataFactory.randomBoolean())
    }
}