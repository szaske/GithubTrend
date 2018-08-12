/**
 * This binds the dataRepository to the Data Module as it is the only class needed in the module.
 * All others are there to create this class.
 *
 */

package com.loc8r.mobile_ui.di.module

import com.loc8r.data.ProjectsDataRepository
import com.loc8r.domain.interfaces.ProjectsRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectsRepository
}